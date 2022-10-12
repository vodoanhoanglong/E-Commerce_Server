package dev.ecommerce.resolvers.auth;

public class FormCreateShopInput {
    public FormCreateShopInput(String name, String address, String phoneNumber, String logo, String banner, String id){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.logo = logo;
        this.banner = banner;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String name;
    String address;
    String phoneNumber;
    String logo;
    String banner;
    String id;
}

