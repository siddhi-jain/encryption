package com.example.demo.Domain;

import com.example.demo.pii;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Address {

    @Id
    private String id;
    @pii
    private String houseNumber;
    private String street;

    public Address() {
    }

    public Address(String id, String houseNumber, String street) {
        this.id = id;
        this.houseNumber = houseNumber;
        this.street = street;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

}
