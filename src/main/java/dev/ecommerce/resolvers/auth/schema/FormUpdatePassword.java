package dev.ecommerce.resolvers.auth.schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FormUpdatePassword {

    public FormUpdatePassword(String newPassword, String password){
        this.newPassword = newPassword;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotNull
    @NotEmpty
    @NotBlank
    String password;
    @NotNull
    @NotEmpty
    @NotBlank
    String newPassword;
}
