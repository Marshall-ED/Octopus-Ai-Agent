package com.hezhu.octopusaiagent.demo.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Marshall
 * @Date 2025/7/29 16:55
 * @Description: 查询扩展器 Demo
 */
@Component
public class MultiQueryExpanderDemo {

    private final ChatClient.Builder chatClientBuilder;

    public MultiQueryExpanderDemo(ChatModel dashscopeChatModel) {
        this.chatClientBuilder = ChatClient.builder(dashscopeChatModel);
    }

    public List<Query> expand(String query) {
        MultiQueryExpander queryExpander = MultiQueryExpander.builder()
                .chatClientBuilder(chatClientBuilder)
                .numberOfQueries(3)
                .build();
        List<Query> queries = queryExpander.expand(new Query("谁是张禾渚啊？"));
        return queries;
    }
}
