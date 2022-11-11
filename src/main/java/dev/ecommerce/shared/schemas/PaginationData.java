package dev.ecommerce.shared.schemas;

public class PaginationData {
    private long totalElements;
    private Integer currentPage;
    private Integer totalPages;
    private Integer perPage;

    public PaginationData(long totalElements, Integer currentPage, Integer totalPages, Integer perPage) {
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.perPage = perPage;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }
}