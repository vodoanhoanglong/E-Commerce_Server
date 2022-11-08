package dev.ecommerce.resolvers.product;

public class FormCreateProductImageInput {

    public FormCreateProductImageInput(String id, String url){
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String id;
    String url;
}
