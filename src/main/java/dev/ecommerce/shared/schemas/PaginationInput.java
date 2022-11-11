package dev.ecommerce.shared.schemas;


public class PaginationInput {
    private int size;
    private  int page;

    public PaginationInput(int size, int page) {
        this.size = size;
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}