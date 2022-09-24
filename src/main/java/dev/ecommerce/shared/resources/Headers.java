package dev.ecommerce.shared.resources;

public enum Headers {
    Authorization("authorization", "Authorization");

    private final String key;
    private final String value;

    Headers(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
