package dev.ecommerce.resolvers.auth;

public class FormCreateProductImageInput {
    public FormCreateProductImageInput(String url, String id){
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String  url;
    String id;
}
