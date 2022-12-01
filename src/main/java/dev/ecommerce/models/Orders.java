package dev.ecommerce.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Orders {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TOTALMONEY")
    private Float totalMoney;

    @Column(name = "QUANTITY")
    private Long quantity;

    @Column(name = "DISCOUNT")
    private Float discount;

    @Column(name = "STATUS", insertable = false)
    private String status;

    @Column(name = "CREATEDAT", insertable = false)
    private java.sql.Timestamp createdAt;

    @Column(name = "UPDATEDAT", insertable = false)
    private java.sql.Timestamp updatedAt;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "UPDATEDBY")
    private String updatedBy;

    @ManyToMany(targetEntity = OrderDetails.class)
    @JoinColumn(name = "ORDERID")
    private List<OrderDetails> orderDetails;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getTotalMoney() {
        return this.totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.sql.Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public java.sql.Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
