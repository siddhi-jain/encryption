package com.example.demo.Repository;

import com.example.demo.Domain.User;
import com.example.demo.Encryption.Decoder;
import com.example.demo.Encryption.Encoder;
import com.example.demo.reflection.Node;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.Encryption.Crypt.cryptFields;
import static com.example.demo.reflection.ReflectionCache.processDocument;

@Repository
public class UserDALImpl implements UserDAL {
    private MongoTemplate mongoTemplate;

    @Autowired
    public UserDALImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public void addNewEUser(User user) {
        List<Node> children=processDocument(user.getClass());
        Node node=new Node("",children, Node.Type.ROOT);
        ObjectMapper mapper=new ObjectMapper();
        try {
            String jsonString=mapper.writeValueAsString(user);
            Document document= Document.parse(jsonString);
            cryptFields(document,node,new Encoder());
            MongoCollection<Document> collection = mongoTemplate.getCollection("user");
            collection.insertOne(document);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        //mongoTemplate.insert(user);
    }

    @Override
    public Flux<Document> findAllEUsers() {
        //Flux<Document> eUser= (Flux<Document>) mongoTemplate.findAll(Document.class);
        MongoCollection<Document> collection = mongoTemplate.getCollection("user");
        FindIterable<Document> eUser= collection.find();
        List<Document> user=new ArrayList<Document>();
        List<Node> children=processDocument((new User()).getClass());
        Node node=new Node("",children,Node.Type.ROOT);
        //ObjectMapper mapper=new ObjectMapper();
        for(Document document:eUser/*.toIterable()*/){

            try {
                //String jsonString=mapper.writeValueAsString(e);
                //Document document= Document.parse(jsonString);
                document.remove("_id");
                cryptFields(document,node,new Decoder());

                //String userString=mapper.writeValueAsString(document);
                //User u=mapper.readValue(userString,User.class);
                user.add(document);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }

        return Flux.fromIterable(user);
    }
}
