package dev.ecommerce.resolvers.categories;

import dev.ecommerce.models.Categories;
import dev.ecommerce.models.CategoryProducts;
import dev.ecommerce.models.Users;
import dev.ecommerce.repositories.CategoriesRepository;
import dev.ecommerce.repositories.CategoryProductsRepository;
import dev.ecommerce.resolvers.auth.FormCreateUserInput;
import dev.ecommerce.shared.Authentication;
import dev.ecommerce.shared.JwtTokenProvider;
import dev.ecommerce.shared.resources.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Controller
@Slf4j
public class CategoryFunction {
    @Autowired
    CategoriesRepository categoriesRepository;
    @Autowired
    CategoryProductsRepository categoryProductsRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    Authentication auth;
    @Autowired
    EntityManager entityManager;
    @Autowired
    HttpServletRequest request;

    @MutationMapping
    @Transactional
    public Map<String, String> createCategories(@Argument FormInputCategories categoriesform){
        HashMap<String, String> map = new HashMap<>();

        try {
            Categories addCategories = new Categories(categoriesform.getAlias(), categoriesform.getName(), categoriesform.getDescription());
            entityManager.persist(addCategories);
            map.put("isSuccess", "true");
            map.put("token", jwtTokenProvider.generateToken(categoriesform.alias.toString()));
            map.put("error", null);
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> createCategoriesProduct(@Argument FormInputCategoriesProduct categoriesProductform){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            CategoryProducts addCategoriesProduct = new CategoryProducts(id.toString(),categoriesProductform.getCategoryAlias(),
                    categoriesProductform.getProductId());
            entityManager.persist(addCategoriesProduct);
            map.put("isSuccess", "true");
            map.put("token", jwtTokenProvider.generateToken(id.toString()));
            map.put("error", null);
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("error", err.getMessage());
        }
        return map;
    }

    @QueryMapping
    public List<Categories> getCategories(){
        try{
            return categoriesRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @QueryMapping
    public List<CategoryProducts> getCategoriesProducts(){
        try{
            return categoryProductsRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @MutationMapping
    @Transactional
    public Map<String, String> updateCategories(@Argument FormInputCategories categoriesform){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<Categories> categoriesFromDB = categoriesRepository.findByAlias(categoriesform.getAlias());;
            categoriesFromDB.get().setName(categoriesform.name);
            categoriesFromDB.get().setDescription(categoriesform.description);
            map.put("name", categoriesFromDB.get().getName());
            map.put("description", categoriesFromDB.get().getDescription());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> updateCategoriesProducts(@Argument FormInputCategoriesProduct categoriesProductform){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<CategoryProducts> categoryProductsFromDB = categoryProductsRepository.findById(categoriesProductform.getId());
            categoryProductsFromDB.get().setCategoryAlias(categoriesProductform.categoryAlias);
            categoryProductsFromDB.get().setProductId(categoriesProductform.productId);
            map.put("categoryAlias", categoryProductsFromDB.get().getCategoryAlias());
            map.put("productId", categoryProductsFromDB.get().getProductId());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }
}
