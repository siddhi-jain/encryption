package com.example.demo.Repository;

import com.example.demo.Domain.User;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDALImpl implements UserDAL {
    private MongoTemplate mongoTemplate;

    @Autowired
    public UserDALImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public void addNewEUser(Document user) {
        MongoCollection<Document> collection = mongoTemplate.getCollection("user");
        collection.insertOne(user);
        //mongoTemplate.insert(user);
    }
}
