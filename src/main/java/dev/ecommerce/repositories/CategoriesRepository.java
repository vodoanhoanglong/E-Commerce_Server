package dev.ecommerce.repositories;

import dev.ecommerce.models.Categories;
import dev.ecommerce.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, String>, JpaSpecificationExecutor<Categories> {

    Optional<Categories> findByAlias(String alias);
}