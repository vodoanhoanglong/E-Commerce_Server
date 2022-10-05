package dev.ecommerce.resolvers.auth;

public class FormCreateCategoryProductInput {
    public FormCreateCategoryProductInput(String categoryAlias, String productId){
        this.categoryAlias = categoryAlias;
        this.productId = productId;
    }

    public String getCategoryAlias() {
        return categoryAlias;
    }

    public void setCategoryAlias(String categoryAlias) {
        this.categoryAlias = categoryAlias;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    String categoryAlias;
    String productId;
}
