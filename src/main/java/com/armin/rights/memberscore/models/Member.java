package com.armin.rights.memberscore.models;

import java.util.Date;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Member {
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String zipcode;
    private String picture;

    public Member() {
    }

    public Member(String firstName, String lastName, Date birthday, String zipcode){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setBirthday(birthday);
        this.setZipcode(zipcode);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picutre) {
        this.picture = picutre;
    }


}
