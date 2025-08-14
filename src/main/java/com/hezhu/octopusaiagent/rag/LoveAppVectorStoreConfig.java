package com.hezhu.octopusaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author Marshall
 * @Date 2025/7/26 14:54
 * @Description: 恋爱大师向量数据库配置（初始化基于内存的向量数据库Bean）
 */
@Configuration
public class LoveAppVectorStoreConfig {
    @Resource
    private MyKeywordEnricher myKeywordEnricher;
    @Resource
    private MyTokenTextSplitter myTokenTextSplitter;
    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Bean
    VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel)
                .build();
        //加载文档
        List<Document> documentList = loveAppDocumentLoader.loadMarkdowns();
        //自主切分
//        List<Document> splitDocuments = myTokenTextSplitter.splitCustomized(documentList);

        //自动补充关键词元信息
        List<Document> enrichDocuments = myKeywordEnricher.enrichDocuments(documentList);

        //初始化向量数据库
        simpleVectorStore.add(enrichDocuments);
        return simpleVectorStore;
    }
}
