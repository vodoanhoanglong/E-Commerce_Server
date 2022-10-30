package dev.ecommerce.resolvers.oders;

public class FromCreateOrdersDetails {
    public FromCreateOrdersDetails(String orderId, String productId, String status) {
        this.orderId = orderId;
        this.productId = productId;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String orderId;
    String productId;
    String status;
}
