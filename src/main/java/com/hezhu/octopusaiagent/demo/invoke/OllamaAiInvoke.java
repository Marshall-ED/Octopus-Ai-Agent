package com.hezhu.octopusaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Author Marshall
 * @Date 2025/7/13 17:05
 * @Description: Spring AI 框架调用 AI 大模型（Ollama）
 */
public class OllamaAiInvoke implements CommandLineRunner {
    @Resource
    private ChatModel ollamaChatModel;
    @Override
    public void run(String... args) throws Exception {
        AssistantMessage output = ollamaChatModel.call(new Prompt("你好，我是伏明武，我很饿"))
                .getResult()
                .getOutput();
        System.out.println(output.getText());
    }
}
