package com.example.demo;

import com.example.demo.Domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

       /* ObjectMapper mapper=new ObjectMapper();
        User user= new User("123","Ajay",1234);
        try {
            String jsonString=mapper.writeValueAsString(user);
            Document document= Document.parse(jsonString);

            String userString=mapper.writeValueAsString(document);
            User user1=mapper.readValue(userString,User.class);
        }

        catch (IOException e){
            e.printStackTrace();
        }
*/
    }

}
