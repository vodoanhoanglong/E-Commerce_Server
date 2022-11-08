package dev.ecommerce.resolvers.auth;

public class FormCreateUserInput {
    public FormCreateUserInput(String fullName, String email, String password, String id, String avatar, String bod, String status,
                               String address, String phoneNumber, String gender) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.id = id;
        this.avatar = avatar;
        this.bod = bod;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String fullName;
    public String email;
    public String password;
    public String id;
    public String bod;
    public String status;
    public String avatar;
    public String address;
    public String phoneNumber;
    public String gender;
}
