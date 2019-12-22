package com.armin.rights.memberscore.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

import static com.armin.rights.memberscore.controllers.MembersController.getFullPictureUrl;


public interface MemberModels {
    @Data
    @XmlRootElement(name = "Members")
    @XmlAccessorType(XmlAccessType.FIELD)
    class Input implements Serializable {
        private static final long serialVersionUID = 2L;

        public Input() {
        }

        public Input(String firstName, String lastName, Date birthday, String zipcode) {
            this.setFirstName(firstName);
            this.setLastName(lastName);
            this.setBirthday(birthday);
            this.setZipcode(zipcode);
        }

        @NotNull(message = "firstName is required")
        @Size(min = 2, max = 30, message = "firstName should have at least 2 characters, and at most 30 characters")
        private String firstName;
        @NotNull(message = "lastName is required")
        @Size(min = 2, max = 30, message = "lastName should have at least 2 characters, and at most 30 characters")
        private String lastName;
        private Date birthday;
        @Pattern(regexp = "^[0-9]{5}$", message = "zipcode is a 5 digit number")
        private String zipcode;

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

        public Member unbox() {
            Member m = new Member();
            m.setFirstName(getFirstName());
            m.setLastName(getLastName());
            m.setBirthday(getBirthday());
            m.setZipcode(getZipcode());
            return m;
        }
    }

    @Data
    @XmlRootElement(name = "Members")
    @XmlAccessorType(XmlAccessType.FIELD)
    class Output {
        private static final long serialVersionUID = 3L;
        private Long id;
        private String firstName;
        private String lastName;
        private Date birthday;
        private String zipcode;
        private String picture;

        private String pictureUrl;

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

        public void setFullname(String name) {
            // TODO: use regex
        }

        public String getFullname() {
            return String.format("%s %s", getFirstName(), getLastName());
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

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public static Output box(Member from) {
            Output o = new Output();
            o.setId(from.getId());
            o.setFirstName(from.getFirstName());
            o.setLastName(from.getLastName());
            o.setBirthday(from.getBirthday());
            o.setZipcode(from.getZipcode());
            o.setPicture(from.getPicture());
            o.setPictureUrl(getFullPictureUrl(from.getPicture()));
            return o;
        }
    }
}
