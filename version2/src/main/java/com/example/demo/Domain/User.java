package com.example.demo.Domain;

import com.example.demo.pii;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public class User {

    @Id
    private String userId;
    private String name;
    @pii
    private Address address;
    @pii
    private String ssn;
    @pii
    private List<String> abb;
    @pii
    private int pan;
    @pii
    private List<Address> addressList;
    @pii
    Map<String,Address> map;
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", ssn='" + ssn + '\'' +
                '}';
    }

    public Map<String, Address> getMap() {
        return map;
    }

    public void setMap(Map<String, Address> map) {
        this.map = map;
    }

    public List<String> getAbb() {
        return abb;
    }

    public void setAbb(List<String> abb) {
        this.abb = abb;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
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

  public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public int getPan() {
        return pan;
    }

    public void setPan(int pan) {
        this.pan = pan;
    }
}
