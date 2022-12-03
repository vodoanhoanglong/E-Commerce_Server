package dev.ecommerce.resolvers.shops.schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FormCreateShopsInput {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FormCreateShopsInput(String id,String name, String address, String phoneNumber, String logo, String banner, String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.logo = logo;
        this.banner = banner;
        this.status = status;
    }

    String id;
    @NotNull
    @NotEmpty
    String name;
    @NotNull
    @NotEmpty
    String address;
    @NotNull
    @NotEmpty
    @NotBlank
    String phoneNumber;

    String logo;
    String banner;
    @NotNull
    @NotEmpty
    String status;
}
