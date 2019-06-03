package com.example.demo.Controller;

import com.example.demo.Domain.User;

import com.example.demo.Repository.UserDAL;
import com.example.demo.Repository.UserDALImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
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



@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private UserDAL userDAL;

    @Autowired
    public UserController(UserDAL userDAL) {
        this.userDAL = userDAL;
    }

    /* @RequestMapping(value = "", method = RequestMethod.GET)
        @ResponseBody
        public Flux <User> getAllUsers(){
            Flux<User> eUser=repository.findAll();
            List<User> user=new ArrayList<User>();
            List<Node> children=processDocument((new User()).getClass());
            Node node=new Node("",children,Node.Type.ROOT);
            ObjectMapper mapper=new ObjectMapper();
            for(User e:eUser.toIterable()){

                try {
                    String jsonString=mapper.writeValueAsString(e);
                    Document document= Document.parse(jsonString);

                    cryptFields(document,node,new Decoder());

                    String userString=mapper.writeValueAsString(document);
                    User u=mapper.readValue(userString,User.class);
                    user.add(u);
                }

                catch (IOException ex){
                    ex.printStackTrace();
                }

            }

            return Flux.fromIterable(user);
        }
    */
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
        ObjectMapper mapper=new ObjectMapper();
       try {
            String jsonString=mapper.writeValueAsString(user);
           Document document= Document.parse(jsonString);
           //DBObject dbObject = (DBObject) JSON.parse(jsonString);
            userDAL.addNewEUser(document);
       }
        catch (IOException e){
            e.printStackTrace();
        }

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
