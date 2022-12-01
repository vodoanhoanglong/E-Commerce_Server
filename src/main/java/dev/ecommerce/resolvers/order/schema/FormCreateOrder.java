package dev.ecommerce.resolvers.order.schema;

import java.util.List;

public class FormCreateOrder {
    private List<String> productIds;

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public FormCreateOrder(List<String> productIds) {
        this.productIds = productIds;
    }
}
