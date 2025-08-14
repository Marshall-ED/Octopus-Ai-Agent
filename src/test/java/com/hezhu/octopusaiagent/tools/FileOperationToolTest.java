package com.hezhu.octopusaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Marshall
 * @Date 2025/8/11 16:45
 * @Description:
 */
@SpringBootTest
class FileOperationToolTest {


    @Test
    void readFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "恋爱大师.txt";
        String result = fileOperationTool.readFile(fileName);
        Assertions.assertNotNull(result);
    }

    @Test
    void writeFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "恋爱大师.txt";
        String content = "章鱼智能体的恋爱大师一级棒！！！";
        String result = fileOperationTool.writeFile(fileName,content);
        Assertions.assertNotNull(result);
    }
}