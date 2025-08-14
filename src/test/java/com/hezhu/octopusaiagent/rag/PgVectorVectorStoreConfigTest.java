package com.hezhu.octopusaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;


/**
 * @Author Marshall
 * @Date 2025/7/28 17:19
 * @Description:
 */
@SpringBootTest
public class PgVectorVectorStoreConfigTest {

    @Resource
    VectorStore pgVectorVectorStore;

    @Test
    void test() {
        List<Document> documents = List.of(
                new Document("菜鸟教程有什么用？学编程啊，做项目啊", Map.of("meta1", "meta1")),
                new Document("菜鸟教程的原创网站https://www.runoob.com/"),
                new Document("章鱼智能体赛高！！！", Map.of("meta2", "meta2")));
        // 添加文档
        pgVectorVectorStore.add(documents);
        // 相似度查询
        List<Document> results = pgVectorVectorStore.similaritySearch(SearchRequest.builder().query("怎么学编程啊？").topK(3).build());
        Assertions.assertNotNull(results);
    }
}
