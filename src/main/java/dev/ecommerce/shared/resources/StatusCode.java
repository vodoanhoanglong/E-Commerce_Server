package dev.ecommerce.shared.resources;

public enum StatusCode {
    Active("active"),
    Deleted("deleted");

    private final String key;


    StatusCode(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
