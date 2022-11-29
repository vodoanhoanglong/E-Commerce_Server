package dev.ecommerce.resolvers.auth.schema;

import javax.validation.constraints.*;

public class FormCreateUserInput {
    public FormCreateUserInput(String fullName, String email, String password, String id, String phoneNumber, String address,
                               String bod, String gender, String avatar) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.bod = bod;
        this.gender = gender;
        this.avatar = avatar;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @NotBlank
    @NotNull
    @NotEmpty
    private String fullName;

    @NotBlank
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    private String password;

    String id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String phoneNumber;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    @NotEmpty
    @NotBlank
    private String gender;

    @NotNull
    @NotEmpty
    @NotBlank
    private String bod;

    @NotNull
    @NotEmpty
    @NotBlank
    private String avatar;
}
