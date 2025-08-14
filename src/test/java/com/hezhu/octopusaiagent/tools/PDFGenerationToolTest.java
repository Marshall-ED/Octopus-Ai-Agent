package com.hezhu.octopusaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Marshall
 * @Date 2025/8/13 15:03
 * @Description:
 */
@SpringBootTest
public class PDFGenerationToolTest {

    @Test
    public void testGeneratePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "恋爱.pdf";
        String content = "恋爱就找章鱼智能体恋爱master";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}
