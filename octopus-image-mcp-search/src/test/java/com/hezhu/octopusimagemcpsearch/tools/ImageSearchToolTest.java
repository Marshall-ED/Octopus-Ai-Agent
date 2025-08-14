package com.hezhu.octopusimagemcpsearch.tools;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Marshall
 * @Date 2025/8/14 7:17
 * @Description:
 */
@SpringBootTest
public class ImageSearchToolTest {

    @Resource
    private ImageSearchTool imageSearchTool;
    @Test
    void searchImage(){
        String result = imageSearchTool.searchImage("computer");
        Assertions.assertNotNull(result);
    }
}
