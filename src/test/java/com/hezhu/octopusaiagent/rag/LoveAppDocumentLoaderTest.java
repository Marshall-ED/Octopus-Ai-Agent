package com.hezhu.octopusaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Marshall
 * @Date 2025/7/26 14:48
 * @Description:
 */
@SpringBootTest
class LoveAppDocumentLoaderTest {

    @Resource
    private  LoveAppDocumentLoader loveAppDocumentLoader;
    @Test
    void loadMarkdowns() {
        loveAppDocumentLoader.loadMarkdowns();
    }
}