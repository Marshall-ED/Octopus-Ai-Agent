package com.hezhu.octopusaiagent.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Marshall
 * @Date 2025/7/26 14:15
 * @Description: 恋爱大师文档加载器
 */
@Component
@Slf4j
public class LoveAppDocumentLoader {
    private final ResourcePatternResolver resourcePatternResolver;

    LoveAppDocumentLoader(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    public List<Document> loadMarkdowns() {
        List<Document> allDocuments = new ArrayList<>();
        try {
            //获取知识库
            Resource[] resources = resourcePatternResolver.getResources("classpath:documents/*.md");
            for (Resource resource : resources) {
                String fileName = resource.getFilename();
                //提取文档倒数第3和第2个字作为标签
                String status = fileName.substring(fileName.length() - 6, fileName.length() - 4);
                //文档加载配置指定读取文档
                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                        .withHorizontalRuleCreateDocument(true)
                        //不读取引用块
                        .withIncludeBlockquote(false)
                        //不读取代码块
                        .withIncludeCodeBlock(false)
                        //文件名作为文档元信息，便于或许知识库精确检索
                        .withAdditionalMetadata("fileName", fileName)
                        .withAdditionalMetadata("status", status)
                        .build();
                MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);
                allDocuments.addAll(reader.get());
            }
        } catch (IOException e) {
            log.error("Markdown 文档加载失败", e);
        }
        return allDocuments;
    }
}
