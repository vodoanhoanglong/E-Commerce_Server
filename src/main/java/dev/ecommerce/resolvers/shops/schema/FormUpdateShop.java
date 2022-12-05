package dev.ecommerce.resolvers.shops.schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FormUpdateShop {
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public FormUpdateShop(String name, String address, String phoneNumber, String logo, String banner, String shopId) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.logo = logo;
        this.banner = banner;
        this.shopId = shopId;
    }

    String name;

    String address;

    String phoneNumber;

    String logo;
    String banner;

    @NotBlank
    @NotEmpty
    @NotNull
    String shopId;
}