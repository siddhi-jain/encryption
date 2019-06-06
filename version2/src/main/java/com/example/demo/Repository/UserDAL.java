package com.example.demo.Repository;

import com.example.demo.Domain.User;
import com.mongodb.DBObject;
import org.bson.Document;
import reactor.core.publisher.Flux;

public interface UserDAL {

    void addNewEUser(User user);
    Flux<Document> findAllEUsers();
}
