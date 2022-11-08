package dev.ecommerce.repositories;

import dev.ecommerce.models.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, String>, JpaSpecificationExecutor<ProductImages> {
    Optional<ProductImages> findById(String id);
}