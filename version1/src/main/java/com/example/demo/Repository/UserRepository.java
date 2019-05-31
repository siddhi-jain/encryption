package com.example.demo.Repository;


import com.example.demo.Domain.User;
import org.bson.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository  extends ReactiveCrudRepository<User, String> {

}
