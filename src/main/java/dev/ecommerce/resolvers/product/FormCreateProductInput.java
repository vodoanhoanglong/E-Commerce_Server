package dev.ecommerce.resolvers.product;

public class FormCreateProductInput {
    public FormCreateProductInput(String id, String name, String description, Float price, Long quantityStore){
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.quantityStore = quantityStore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    String id;
    String name;
    String description;
    Float price;
    Long quantityStore;
}
