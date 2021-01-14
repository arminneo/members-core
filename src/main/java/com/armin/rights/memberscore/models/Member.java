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
}
