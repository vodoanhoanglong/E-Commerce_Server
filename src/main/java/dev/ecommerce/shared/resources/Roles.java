package dev.ecommerce.shared.resources;

public enum Roles {
    ADMIN("admin"),
    USER("user"),
    ANONYMOUS("anonymous");

    private final String value;

    Roles(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
