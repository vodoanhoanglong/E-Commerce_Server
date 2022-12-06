package dev.ecommerce.resolvers.order.schema;

import dev.ecommerce.models.Products;

import java.util.List;

public class FormCreateOrder {
    private List<ProductOrder> products;
    private String deliveryAddress;
    private String paymentType;

    private int shipCost;

    public FormCreateOrder(List<ProductOrder> products, String deliveryAddress,
                           String paymentType, int shipCost) {
        this.products = products;
        this.deliveryAddress = deliveryAddress;
        this.paymentType = paymentType;
        this.shipCost = shipCost;
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

    public int getShipCost() {
        return shipCost;
    }

    public void setShipCost(int shipCost) {
        this.shipCost = shipCost;
    }

    public List<ProductOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrder> products) {
        this.products = products;
    }

    public static class ProductOrder extends Products {
        private String productId;
        private Long quantity;

        public ProductOrder(String productId, long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Long getQuantity() {
            return quantity;
        }

        public void setQuantity(long quantity) {
            this.quantity = quantity;
        }
    }
}
