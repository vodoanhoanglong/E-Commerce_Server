package dev.ecommerce.resolvers.order.handler;

import dev.ecommerce.models.Categories;
import dev.ecommerce.models.OrderDetails;
import dev.ecommerce.repositories.CategoriesRepository;
import dev.ecommerce.repositories.OrderDetailsRepository;
import dev.ecommerce.repositories.OrdersRepository;
import dev.ecommerce.resolvers.category.schema.FormCreateCategoryInput;
import dev.ecommerce.shared.auth.JwtTokenProvider;
import dev.ecommerce.shared.errors.CustomMessageError;
import dev.ecommerce.shared.resources.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

public class OrderResolver {
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    EntityManager entityManager;

//    @MutationMapping
//    @Transactional
//    public Map<String, Object> createCategory(@Argument @Valid FormCreateCategoryInput form){
//        try {
//            HashMap<String, Object> category = new HashMap<>();
//            Categories isExisted = categoriesRepository.findCategoryByAlias(form.getAlias());
//            if (isExisted != null) {
//                throw new Error(Errors.CategoriesAlreadyExist.getValue());
//            }
//            Categories addCategory = new Categories(form.getAlias(), form.getName()
//                    , form.getDescription());
//            entityManager.persist(addCategory);
//            category.put("isSuccess", true);
//            return category;
//        }catch (Error err){
//            throw new CustomMessageError(err.getMessage());
//        }
//    }

}
