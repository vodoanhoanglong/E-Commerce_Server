package dev.ecommerce.resolvers.order.schema;

import javax.validation.constraints.NotEmpty;

public class FormCreateOrderInput {
    public FormCreateOrderInput(Float totalMoney, Float discount, Long quantity){
        this.totalMoney = totalMoney;
        this.discount = discount;
        this.quantity = quantity;
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
