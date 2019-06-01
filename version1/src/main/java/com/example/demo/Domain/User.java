package com.example.demo.Domain;

import com.example.demo.pii;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

    @Id
    private String userId;
    private String name;

   // private Address address;
    @pii
    private String ssn;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", ssn='" + ssn + '\'' +
                '}';
    }

    public User(){}

    public User(String userId, String name/*, Address address*/, String ssn) {
        this.userId = userId;
        this.name = name;
       //this.address = address;
        this.ssn = ssn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
*/
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }


}
