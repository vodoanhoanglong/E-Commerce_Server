package dev.ecommerce.models;

import dev.ecommerce.resolvers.order.schema.PaymentType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
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
    private Integer quantity;

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

    @Column(name = "DELIVERYADDRESS")
    private String deliveryAddress;

    @Column(name = "DELIVERYSTATUS")
    @Generated(GenerationTime.INSERT)
    private String deliveryStatus;

    @Column(name = "ISPAID")
    private int isPaid;

    @Column(name = "PAYMENTAT")
    private java.sql.Timestamp paymentAt;

    @Column(name = "PAYMENTTYPE")
    private String paymentType;

    @Column(name = "SHIPCOST")
    private int shipCost;


    @OneToMany(targetEntity = OrderDetails.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "ORDERID", insertable = false, updatable = false)
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

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
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

    public Orders() {
    }

    public Orders(String id, Float totalMoney, Integer quantity, Float discount,
                  String deliveryAddress, int isPaid, Timestamp paymentAt,
                  String paymentType, String createdBy, int shipCost) {
        this.id = id;
        this.totalMoney = totalMoney;
        this.quantity = quantity;
        this.discount = discount;
        this.deliveryAddress = deliveryAddress;
        this.isPaid = isPaid;
        this.paymentAt = paymentAt;
        this.shipCost = shipCost;
        this.paymentType = paymentType;
        this.createdBy = createdBy;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public Timestamp getPaymentAt() {
        return paymentAt;
    }

    public void setPaymentAt(Timestamp paymentAt) {
        this.paymentAt = paymentAt;
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
}
