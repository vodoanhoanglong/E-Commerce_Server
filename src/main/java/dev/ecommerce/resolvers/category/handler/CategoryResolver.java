package dev.ecommerce.resolvers.category.handler;

import dev.ecommerce.models.Categories;
import dev.ecommerce.models.Products;
import dev.ecommerce.repositories.CategoriesRepository;
import dev.ecommerce.resolvers.category.schema.FormCreateCategoryInput;
import dev.ecommerce.shared.auth.JwtTokenProvider;
import dev.ecommerce.shared.errors.CustomMessageError;
import dev.ecommerce.shared.resources.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class CategoryResolver {
    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    EntityManager entityManager;

    @MutationMapping
    @Transactional
    public Map<String, Object> createCategory(@Argument @Valid FormCreateCategoryInput form){
        try {
            HashMap<String, Object> category = new HashMap<>();
            Categories isExisted = categoriesRepository.findCategoryByAlias(form.getAlias());
            if (isExisted != null) {
                throw new Error(Errors.CategoriesAlreadyExist.getValue());
            }
            Categories addCategory = new Categories(form.getAlias(), form.getName()
                    , form.getDescription());
            entityManager.persist(addCategory);
            category.put("isSuccess", true);
            return category;
        }catch (Error err){
            throw new CustomMessageError(err.getMessage());
        }
    }

    @QueryMapping
    public List<Categories> getCategory(){
        try{
            return categoriesRepository.findAll().stream().toList();
        }catch(Error err){
            throw new CustomMessageError(err.getMessage());
        }
    }

    @MutationMapping
    @Transactional
    public Map<String, String> updateCategory(@Argument @Valid FormCreateCategoryInput form){
        HashMap<String, String> category = new HashMap<>();

        try {
            Categories categoriesFromDB = categoriesRepository.findByAlias(form.getAlias());;
            categoriesFromDB.setName(form.getName());
            categoriesFromDB.setDescription(form.getDescription());
            category.put("name", categoriesFromDB.getName());
            category.put("description", categoriesFromDB.getDescription());
            return category;
        }catch (Error err){
            throw new CustomMessageError(err.getMessage());
        }
    }
}
