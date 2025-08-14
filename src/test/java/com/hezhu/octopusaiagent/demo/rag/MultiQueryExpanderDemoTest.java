package com.hezhu.octopusaiagent.demo.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.rag.Query;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Marshall
 * @Date 2025/7/29 17:06
 * @Description:
 */
@SpringBootTest
class MultiQueryExpanderDemoTest {

    @Resource
    private MultiQueryExpanderDemo multiQueryExpanderDemo;


    @Test
    void expand() {
        List<Query> queries = multiQueryExpanderDemo.expand("啥是张禾渚啊啊啊啊？？？哈哈哈哈哈");
        Assertions.assertNotNull(queries);
    }
}