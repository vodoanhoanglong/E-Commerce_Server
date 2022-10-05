package dev.ecommerce.resolvers.auth;

public class FormCreateUserInput {
    public FormCreateUserInput(String fullName, String email, String password, String address, String gender, String phoneNumber, String id) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    String fullName;
    String email;
    String password;
    String address;
    String gender;
    String phoneNumber;
    String id;
}
