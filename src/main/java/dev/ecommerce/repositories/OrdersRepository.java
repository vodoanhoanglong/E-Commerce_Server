package dev.ecommerce.repositories;

import dev.ecommerce.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String>, JpaSpecificationExecutor<Orders> {
}