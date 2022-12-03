package dev.ecommerce.resolvers.order.schema;

public enum PaymentType {
    Online("online"),
    Offline("offline");

    private final String type;

    PaymentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
