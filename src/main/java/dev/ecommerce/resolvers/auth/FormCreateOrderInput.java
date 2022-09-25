package dev.ecommerce.resolvers.auth;

public class FormCreateOrderInput {
    public  FormCreateOrderInput(Float totalMoney, Long quantity, Float discount){
        this.totalMoney = totalMoney;
        this.quantity = quantity;
        this.discount = discount;
    }

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    Float totalMoney;
    Float discount;
    Long quantity;
}
