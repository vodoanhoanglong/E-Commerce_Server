package dev.ecommerce.resolvers.auth;

public class FormCreateProductImageInput {
    public FormCreateProductImageInput(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String  url;
}
