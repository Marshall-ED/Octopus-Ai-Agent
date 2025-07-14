package com.hezhu.octopusaiagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * @Author Marshall
 * @Date 2025/7/13 15:07
 * @Description:
 */
public class LangChainAiInvoke {
    public static void main(String[] args) {
        ChatLanguageModel qwenChatModel = QwenChatModel.builder()
                .apiKey(TestApiKey.API_KEY)
                .modelName("qwen-max")
                .build();

        String answer = qwenChatModel.chat("你好，我是张禾渚");
        System.out.println(answer);
    }
}
