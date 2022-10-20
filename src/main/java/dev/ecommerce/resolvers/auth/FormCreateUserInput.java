package dev.ecommerce.resolvers.auth;

public class FormCreateUserInput {
    public FormCreateUserInput(String fullName, String email, String password, String id) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.id = id;
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

    String fullName;
    String email;
    String password;
    String id;
}
