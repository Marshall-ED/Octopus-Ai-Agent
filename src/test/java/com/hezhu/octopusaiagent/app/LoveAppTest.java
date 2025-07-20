package com.hezhu.octopusaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Author Marshall
 * @Date 2025/7/20 10:32
 * @Description:
 */
@SpringBootTest
public class LoveAppTest {
    @Resource
    private LoveApp loveApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        //第一轮
        String message = "你好，我是张禾渚";
        String answer = loveApp.doChat(message, chatId);
        assertNotNull(answer);

        //第二轮
        message = "我的另一半（Marshall）最近在和我闹矛盾";
        answer = loveApp.doChat(message, chatId);
        assertNotNull(answer);

        //第三轮
        message = "我的另一半叫什么来着，我刚才告诉过你";
        answer = loveApp.doChat(message, chatId);
        assertNotNull(answer);

    }

    @Test
    void doChatWithReport(){
        String chatId = UUID.randomUUID().toString();
        //第一轮
        String message = "你好，我是张禾渚,我想让另一半更爱我，但我不知道怎么做";
        LoveApp.LoveReport  answer = loveApp.doChatWithReport(message,chatId);
        assertNotNull(answer);
    }
}
