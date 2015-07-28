package com.tecacet.movielens.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//user id | age | gender | occupation | zip code
public class User {

    private long id;
    private int age;
    @NotNull
    private String gender; //TODO enum
    
    private String occupation;
    @Size(min=5)
    private String zipCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
