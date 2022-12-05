package dev.ecommerce.resolvers.product.schema;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ProductReqBody {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private float price;
    private List<String> images;

    public ProductReqBody(String name, String description, float price, List<String> images) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}