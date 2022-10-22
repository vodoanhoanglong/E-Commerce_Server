package dev.ecommerce.resolvers.product;

import javax.persistence.Column;

public class FormCreateProductImage {


    public FormCreateProductImage(String url, String status) {
        this.url = url;
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String url;
    String status;


}
