package com.ForMonk2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import com.ForMonk2.utils.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;

@Configuration
public class MyMongoConfigs extends AbstractMongoConfiguration {


    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.uri}")
    private String uriString;

    @Override
    public MongoClient mongoClient() {
    	MongoClientURI uri = new MongoClientURI(uriString);
    	MongoClient mongoClient = new MongoClient(uri);
        return mongoClient;

    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Bean
    MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoConverter converter) {
      MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
      mongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
      mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
      return mongoTemplate;
    }
}