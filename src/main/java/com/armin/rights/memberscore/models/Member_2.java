package com.armin.rights.memberscore.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Member_2 {
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String zipcode;
    private String picture;

    public Member_2() {
    }

    public Member_2(String firstName, String lastName, Date birthday, String zipcode){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setBirthday(birthday);
        this.setZipcode(zipcode);
    }
}
