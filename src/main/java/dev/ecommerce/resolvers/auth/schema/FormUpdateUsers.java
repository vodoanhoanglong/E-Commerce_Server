package dev.ecommerce.resolvers.auth.schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FormUpdateUsers {
    public FormUpdateUsers(String id, String fullName, String address, String bod, String gender, String phoneNumber, String avatar){
            this.id = id;
            this.fullName = fullName;
            this.address = address;
            this.bod = bod;
            this.gender = gender;
            this.phoneNumber = phoneNumber;
            this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    String id;

    String fullName;

    String address;

    String bod;

    String gender;

    String phoneNumber;

    String avatar;
}
