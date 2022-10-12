package dev.ecommerce.repositories;

import dev.ecommerce.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String>, JpaSpecificationExecutor<Products> {

    Optional<Products> findById(String id);
}