package dev.ecommerce.resolvers.product.schema;

public class ProductQtyReqBody {
    private String product_id;
    private Long quantity;

    public ProductQtyReqBody(String product_id, Long quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public String getProductId() {
        return product_id;
    }

    public void setProductId(String product_id) {
        this.product_id = product_id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
