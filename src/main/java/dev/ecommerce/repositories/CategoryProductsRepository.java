package dev.ecommerce.repositories;

import dev.ecommerce.models.CategoryProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryProductsRepository extends JpaRepository<CategoryProducts, String>, JpaSpecificationExecutor<CategoryProducts> {
}