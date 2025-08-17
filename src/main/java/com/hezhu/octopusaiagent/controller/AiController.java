package com.hezhu.octopusaiagent.controller;

import com.hezhu.octopusaiagent.agent.OctopusManus;
import com.hezhu.octopusaiagent.app.LoveApp;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;

/**
 * @Author Marshall
 * @Date 2025/8/17 14:59
 * @Description:
 */
@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private LoveApp loveApp;

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;

    @GetMapping("/love_app/chat/sync")
    public String doChatWithLoveAppSync(String message, String chatId) {
        return loveApp.doChat(message, chatId);
    }

    @GetMapping(value = "/love_app/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithLoveAppSSE(
            @RequestParam String message,
            @RequestParam String chatId) {
        return loveApp.doChatByStream(message, chatId);
    }

    @GetMapping(value = "/love_app/chat/server_sent_event")
    public Flux<ServerSentEvent<String>> doChatWithLoveAppServerSentEvent(String message, String chatId) {
        return loveApp.doChatByStream(message, chatId)
                .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }

    @GetMapping("/love_app/chat/sse/emitter")
    public SseEmitter doChatWithLoveAppSseEmitter(String message, String chatId) {
        //创建一个较长时间的 SseEmitter
        SseEmitter emitter = new SseEmitter(180000L);//3分钟超时
        //获取 Flux 数据流并直接订阅
        loveApp.doChatByStream(message, chatId)
                .subscribe(
                        //处理每条消息
                        chunk -> {
                            try {
                                emitter.send(chunk);
                            } catch (IOException e) {
                                emitter.completeWithError(e);
                            }
                        },
                        //处理错误
                        emitter::completeWithError,
                        //处理完成
                        emitter::complete
                );
        //返回emitter
        return emitter;
    }

    /**
     * 流式调用 OctopusManus 超级智能体
     * @param message
     * @return
     */
    @GetMapping("/manus/chat")
    public SseEmitter doChatWithManus(String message) {
        OctopusManus octopusManus = new OctopusManus(allTools, dashscopeChatModel);
        return octopusManus.runStream(message);
    }
}
