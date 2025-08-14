package com.hezhu.octopusaiagent;

import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = PgVectorStoreAutoConfiguration.class)
public class OctopusAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(OctopusAiAgentApplication.class, args);
    }

}
