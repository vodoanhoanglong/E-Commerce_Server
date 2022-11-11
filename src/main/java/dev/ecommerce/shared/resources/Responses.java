package dev.ecommerce.shared.resources;

public enum Responses {
    Pagination("pagination"),
    Data("data"),
    Message("message");

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    Responses(String key) {
        this.key = key;
    }
}
