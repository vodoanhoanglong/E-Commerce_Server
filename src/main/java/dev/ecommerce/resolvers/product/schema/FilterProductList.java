package dev.ecommerce.resolvers.product.schema;

import dev.ecommerce.shared.schemas.PaginationInput;

import java.util.List;

public class FilterProductList {
    private List<String> categoryAliases;
    private PaginationInput paginate;

    public List<String> getCategoryAliases() {
        return categoryAliases;
    }

    public void setCategoryAliases(List<String> categoryAliases) {
        this.categoryAliases = categoryAliases;
    }

    public PaginationInput getPaginate() {
        return paginate;
    }

    public void setPaginate(PaginationInput paginate) {
        this.paginate = paginate;
    }

    public FilterProductList(List<String> categoryAliases, PaginationInput paginate) {
        this.categoryAliases = categoryAliases;
        this.paginate = paginate;
    }
}
