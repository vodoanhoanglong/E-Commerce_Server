package dev.ecommerce.resolvers.auth;

public class FormCreateOrderDetailsInput {
    public FormCreateOrderDetailsInput(String orderId, String productId){
        this.orderId = orderId;
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    String orderId;
    String productId;
}
