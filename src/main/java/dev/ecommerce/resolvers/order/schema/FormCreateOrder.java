package dev.ecommerce.resolvers.order.schema;

import java.util.List;

public class FormCreateOrder {
    private final List<String> productIds;
    private String deliveryAddress;
    private String paymentType;

    public List<String> getProductIds() {
        return productIds;
    }

    public FormCreateOrder(List<String> productIds, String deliveryAddress, String paymentType) {
        this.productIds = productIds;
        this.deliveryAddress = deliveryAddress;
        this.paymentType = paymentType;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
