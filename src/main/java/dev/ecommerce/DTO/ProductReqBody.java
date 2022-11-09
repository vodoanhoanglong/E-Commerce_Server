package dev.ecommerce.DTO;

import java.util.List;

public class ProductReqBody {
    private String name;
    private String description;
    private float price;
//    private List<String> imageList;

    public ProductReqBody(String name, String description, float price) {
        this.name = name;
        this.description = description;
        this.price = price;
//        this.imageList = imageList;
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
}
