package dev.ecommerce.resolvers.categories;

public class FormInputCategoriesProduct {
    public FormInputCategoriesProduct(String id , String categoryAlias, String productId){
        this.id = id;
        this.categoryAlias = categoryAlias;
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    String id;
    String categoryAlias;
    String productId;

}
