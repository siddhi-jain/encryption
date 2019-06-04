package com.example.demo.Config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableReactiveMongoRepositories
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Bean
    public MongoClient reactiveMongoClient(){
        return MongoClients.create();
    }

    @Override
    protected String getDatabaseName(){
        return "test";
    }

    /*@Override
    protected Collection<String> getMappingBasePackages() {
        //return super.getMappingBasePackages();
        //return Stream.of("uk.co.whatever.domain").collect(Collectors.toList());
        Package mappingBasePackage = getClass().getPackage();
        return Collections.singleton(mappingBasePackage == null ? null : mappingBasePackage.getName());
    }*/
}
