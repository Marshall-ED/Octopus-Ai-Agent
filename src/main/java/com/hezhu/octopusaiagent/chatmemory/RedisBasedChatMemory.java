package com.hezhu.octopusaiagent.chatmemory;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Marshall
 * @Date 2025/7/20 18:17
 * @Description:
 */
@Service
public class RedisBasedChatMemory implements ChatMemory {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final static String KEY_PREFIX = "chat:memory";

    @Override
    public void add(String conversationId, List<Message> messages) {
        String key = KEY_PREFIX + conversationId;
        try {
            String json = new ObjectMapper().writeValueAsString(messages);
            redisTemplate.opsForList().rightPush(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        String key = KEY_PREFIX + conversationId;
        List<String> jsonList = redisTemplate.opsForList()
                .range(key, -lastN, -1);
        return jsonList.stream()
                .map(json -> {
                    try {
                        return new ObjectMapper().readValue(json, Message.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @Override
    public void clear(String conversationId) {
        redisTemplate.delete(KEY_PREFIX + conversationId);
    }
}
