package dev.ecommerce.repositories;

import dev.ecommerce.models.Shops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopsRepository extends JpaRepository<Shops, String>, JpaSpecificationExecutor<Shops> {

    Optional<Shops> findById (String id);
}