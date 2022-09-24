package dev.ecommerce.resolvers.auth;

public class FormLoginInput {
    private String email;
    private String password;

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

    public FormLoginInput(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
