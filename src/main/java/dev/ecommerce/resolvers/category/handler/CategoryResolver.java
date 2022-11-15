package dev.ecommerce.resolvers.category.handler;

import dev.ecommerce.models.Categories;
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
    public Map<String, Object> createCategory(@Argument @Valid FormCreateCategoryInput categoryform){
        try {
            HashMap<String, Object> cate = new HashMap<>();
            if (cate != null) {
                throw new Error(Errors.CategoriesAlreadyExist.getValue());
            }
            Categories addCategory = new Categories(categoryform.getAlias(), categoryform.getName()
                    , categoryform.getDescription());
            entityManager.persist(addCategory);
            cate.put("isSuccess", true);
            return cate;
        }catch (Error err){
            throw new CustomMessageError(err.getMessage());
        }
    }

    @QueryMapping
    public List<Categories> getCategory(){
        try{
            return categoriesRepository.findAll().stream().toList();
        }catch(Error error){
            throw new CustomMessageError(error.getMessage());
        }
    }

    @MutationMapping
    @Transactional
    public Map<String, String> updateCategory(@Argument FormCreateCategoryInput categoryform){
        HashMap<String, String> cate = new HashMap<>();

        try {
            Categories categoriesFromDB = categoriesRepository.findByAlias(categoryform.getAlias());;
            categoriesFromDB.setName(categoryform.getName());
            categoriesFromDB.setDescription(categoryform.getDescription());
            cate.put("name", categoriesFromDB.getName());
            cate.put("description", categoriesFromDB.getDescription());
        }catch (Error err){
            cate.put("isSuccess", "false");
            cate.put("token", null);
            cate.put("error", err.getMessage());
        }
        return cate;
    }
}
