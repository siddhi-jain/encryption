package com.example.demo.Controller;

import com.example.demo.Domain.User;

import com.example.demo.Encryption.Decoder;
import com.example.demo.Encryption.Encoder;
import com.example.demo.Repository.UserDAL;
import com.example.demo.Repository.UserDALImpl;
import com.example.demo.reflection.Node;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.Encryption.Crypt.cryptFields;
import static com.example.demo.reflection.ReflectionCache.processDocument;


@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private UserDAL userDAL;

    @Autowired
    public UserController(UserDAL userDAL) {
        this.userDAL = userDAL;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Flux <Document> getAllUsers(){
        return userDAL.findAllEUsers();

        }

   /* @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Mono<Document> getUserById(@PathVariable String userId){
        LOG.info("Getting user with ID: {}.", userId);
        Mono<Document> documentMono=repository.findById(userId);
        //Document d=decrypt(documentMono);
        //return Mono.just(d);
    }
*/
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void addNewUser(@RequestBody User user){
        LOG.info("Saving user.");
        userDAL.addNewEUser(user);

        /*List<Node> children=processDocument(user.getClass());

        Node node=new Node("",children,Node.Type.ROOT);
        ObjectMapper mapper=new ObjectMapper();
        try {
            String jsonString=mapper.writeValueAsString(user);

            Document document= Document.parse(jsonString);
            cryptFields(document,node,new Encoder());

            String userString=mapper.writeValueAsString(document);
            User u=mapper.readValue(userString,User.class);

            repository.save(u).block();
        }

        catch (IOException e){
            e.printStackTrace();
        }

*/

    }


}
