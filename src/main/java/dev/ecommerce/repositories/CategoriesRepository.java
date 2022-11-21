package dev.ecommerce.repositories;

import dev.ecommerce.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, String>, JpaSpecificationExecutor<Categories> {

    Categories findByAlias(String alias);

    Categories findCategoryByAlias(String alias);
}