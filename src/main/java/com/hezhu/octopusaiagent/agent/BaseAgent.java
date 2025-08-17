package com.hezhu.octopusaiagent.agent;

import cn.hutool.core.util.StrUtil;
import com.hezhu.octopusaiagent.agent.model.AgentState;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author Marshall
 * @Date 2025/8/16 15:53
 * @Description: 抽象基础代理类，用于管理代理状态和执行流程
 */
@Slf4j
@Data
public abstract class BaseAgent {
    /**
     * 核心属性
     */
    private String name;

    /**
     * 提示
     */
    private String systemPrompt;
    private String nextStepPrompt;

    /**
     * 状态
     */
    private AgentState state = AgentState.IDLE;

    //执行状态
    private int maxSteps = 5;
    private int currentStep = 0;

    //LLM
    private ChatClient chatClient;

    /**
     * Memory(需要自主维护会话上下文)
     */
    private List<Message> messageList = new ArrayList<>();

    /**
     * 运行（同步输出）
     *
     * @param userPrompt
     * @return
     */
    public String run(String userPrompt) {
        if (this.state != AgentState.IDLE) {
            throw new RuntimeException("Cannot run agent from state" + this.state);
        }
        if (StrUtil.isBlank(userPrompt)) {
            throw new RuntimeException("Cannot run agent with empty user prompt");
        }
        //更改状态
        state = AgentState.RUNNING;
        //记录消息上下文
        messageList.add(new UserMessage(userPrompt));
        //保存结果列表
        List<String> results = new ArrayList<>();

        try {
            for (int i = 0; i < maxSteps && state != AgentState.FINISHED; i++) {
                int stepNumber = i + 1;
                currentStep = stepNumber;
                log.info("Executing step" + stepNumber + "/" + maxSteps);
                //单步执行
                String stepResult = step();
                String result = "Step" + stepNumber + "：" + stepResult;
                results.add(result);
            }

            //检查是否超出步骤限制
            if (currentStep >= maxSteps) {
                state = AgentState.FINISHED;
                results.add("Terminated: Reached max Steps (" + maxSteps + ")");
            }
            return String.join("\n", results);
        } catch (Exception e) {
            state = AgentState.ERROR;
            log.error("Error executing agent", e);
            return "执行错误" + e.getMessage();
        } finally {
            //清理资源
            this.cleanUp();
        }

    }

    /**
     * 运行代理（流式输出）
     *
     * @param userPrompt
     * @return
     */
    public SseEmitter runStream(String userPrompt) {

        //创建一个时间较长的 SseEmitter
        SseEmitter sseEmitter = new SseEmitter(180000L);// 3 分钟超时

        //使用线程异步处理，避免阻塞主线程
        CompletableFuture.runAsync(() -> {
            //基础校验
            try {
                if (this.state != AgentState.IDLE) {
                    sseEmitter.send("错误：无法从状态运行代理：" + this.state);
                    sseEmitter.complete();
                    return;
                }
                if (StrUtil.isBlank(userPrompt)) {
                    sseEmitter.send("错误：不能使用空提示词运行代理");
                    sseEmitter.complete();
                    return;
                }
            } catch (IOException e) {
                sseEmitter.completeWithError(e);
            }
            //更改状态
            state = AgentState.RUNNING;
            //记录消息上下文
            messageList.add(new UserMessage(userPrompt));
            //保存结果列表
            List<String> results = new ArrayList<>();

            try {
                for (int i = 0; i < maxSteps && state != AgentState.FINISHED; i++) {
                    int stepNumber = i + 1;
                    currentStep = stepNumber;
                    log.info("Executing step" + stepNumber + "/" + maxSteps);
                    //单步执行
                    String stepResult = step();
                    String result = "Step" + stepNumber + "：" + stepResult;
                    results.add(result);
                    //输出当前每一步的结果到sse
                    sseEmitter.send(result);
                }

                //检查是否超出步骤限制
                if (currentStep >= maxSteps) {
                    state = AgentState.FINISHED;
                    results.add("Terminated: Reached max Steps (" + maxSteps + ")");
                    sseEmitter.send("执行结束，达到最大步骤（" + maxSteps + "）");
                }
                //正常完成
                sseEmitter.complete();
            } catch (Exception e) {
                state = AgentState.ERROR;
                log.error("Error executing agent", e);
                try {
                    sseEmitter.send("执行错误：" + e.getMessage());
                    sseEmitter.complete();
                } catch (IOException ex) {
                    sseEmitter.completeWithError(ex);
                }
//                sseEmitter.complete();
            } finally {
                //清理资源
                this.cleanUp();
            }

        });

        //设置超时回调
        sseEmitter.onTimeout(() -> {
            this.state = AgentState.ERROR;
            this.cleanUp();
            log.warn("SSE connection timeout");
        });
        //设置完成回调
        sseEmitter.onCompletion(() -> {
            if (this.state == AgentState.RUNNING) {
                this.state = AgentState.FINISHED;
            }
            this.cleanUp();
            log.info("SSE connection comleted");
        });
        return sseEmitter;

    }

    /**
     * 执行单个步骤
     *
     * @return 步骤执行结果
     */
    public abstract String step();

    /**
     * 清理资源
     */
    protected void cleanUp() {
        // 子类可重写
    }
}
