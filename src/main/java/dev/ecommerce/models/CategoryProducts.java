package dev.ecommerce.models;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORY_PRODUCTS")
public class CategoryProducts {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "CATEGORYALIAS")
    private String categoryAlias;

    @Column(name = "PRODUCTID")
    private String productId;

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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryAlias() {
        return this.categoryAlias;
    }

    public void setCategoryAlias(String categoryAlias) {
        this.categoryAlias = categoryAlias;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
}
