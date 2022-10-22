package dev.ecommerce.resolvers.product;

import dev.ecommerce.models.Products;
import dev.ecommerce.models.Users;
import dev.ecommerce.repositories.ProductImagesRepository;
import dev.ecommerce.repositories.ProductsRepository;
import dev.ecommerce.resolvers.auth.FormCreateUserInput;
import dev.ecommerce.shared.Authentication;
import dev.ecommerce.shared.JwtTokenProvider;
import dev.ecommerce.shared.resources.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
public class ProductsResolver {
    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ProductImagesRepository productImagesRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    HttpServletRequest request;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    Authentication auth;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @QueryMapping
    public List<Products> getProducts(){
        try{
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return productsRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @MutationMapping
    @Transactional
    public Map<String, String> createProducts(@Argument FormCreateProducts productsForm){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            Products addProducts = new Products(id.toString(), productsForm.getName(), productsForm.getDescription(), productsForm.getPrice(),productsForm.getQuantityStore(),productsForm.getStatus());
            entityManager.persist(addProducts);
            map.put("isSuccess", "true");
            map.put("token", jwtTokenProvider.generateToken(id.toString()));
            map.put("error", null);
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }
}
