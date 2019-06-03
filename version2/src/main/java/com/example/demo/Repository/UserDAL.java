package com.example.demo.Repository;

import com.example.demo.Domain.User;
import com.mongodb.DBObject;
import org.bson.Document;

public interface UserDAL {

    void addNewEUser(Document user);
}
