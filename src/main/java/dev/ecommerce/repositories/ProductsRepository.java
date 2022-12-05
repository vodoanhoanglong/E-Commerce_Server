package dev.ecommerce.repositories;

import dev.ecommerce.models.Orders;
import dev.ecommerce.models.Products;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductsRepository extends JpaRepository<Products, String>, JpaSpecificationExecutor<Products> {
    @NotNull Page<Products> findAll(@NotNull Pageable pageable);
    @NotNull Page<Products> findAllByCategoryAliasIn(List<String> categoryAliases, @NotNull Pageable pageable);
    Products findProductsByName(String productName);
    Products getProductsById(@NotNull String productId);
    List<Products> findAllProductByIdIn(List<String> productIds);
}