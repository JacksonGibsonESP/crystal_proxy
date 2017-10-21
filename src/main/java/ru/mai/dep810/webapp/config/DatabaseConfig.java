package ru.mai.dep810.webapp.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@Configuration
public class DatabaseConfig {

    @Bean
    MongoClient mongoClient() {
        return new MongoClient("localhost", 27017);
    }

    @Bean
    MongoOperations mongoOperations(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "twitter");
    }
}
