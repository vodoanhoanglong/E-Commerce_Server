package dev.ecommerce.repositories;

import dev.ecommerce.models.Shops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopsRepository extends JpaRepository<Shops, String>, JpaSpecificationExecutor<Shops> {
    Shops getShopsByCreatedBy(String userId);
    List<Shops> findAllByCreatedBy(String userId);

}