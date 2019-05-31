package com.example.demo.Domain;

import com.example.demo.pii;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Address {

    @Id
    private int id;
    @pii
    private String houseNumber;
    private String street;

    public Address(int id, String houseNumber, String street) {
        this.id = id;
        this.houseNumber = houseNumber;
        this.street = street;
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
