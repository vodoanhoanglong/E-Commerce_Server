package dev.ecommerce.resolvers.oders;

public class FormCreateOrders {
    public FormCreateOrders(Float totalMoney, Long quantity, Float discount, String status, String shopId) {
        this.totalMoney = totalMoney;
        this.quantity = quantity;
        this.discount = discount;
        this.status = status;
        this.shopId = shopId;
    }

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    Float totalMoney;
    Long quantity;
    Float discount;
    String status;
    String shopId;
}
