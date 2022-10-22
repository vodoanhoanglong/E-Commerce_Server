package dev.ecommerce.resolvers.product;

public class FormCreateProducts {
    public FormCreateProducts(String name, String description, Float price, Long quantityStore, String status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityStore = quantityStore;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getQuantityStore() {
        return quantityStore;
    }

    public void setQuantityStore(Long quantityStore) {
        this.quantityStore = quantityStore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String name;
    String description;
    Float price;
    Long quantityStore;
    String status;

}
