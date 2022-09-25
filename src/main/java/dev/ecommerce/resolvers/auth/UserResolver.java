package dev.ecommerce.resolvers.auth;

import dev.ecommerce.models.*;
import dev.ecommerce.repositories.*;
import dev.ecommerce.shared.Authentication;
import dev.ecommerce.shared.JwtTokenProvider;
import dev.ecommerce.shared.resources.Errors;
import dev.ecommerce.shared.resources.StatusCode;
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
public class UserResolver {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ShopsRepository shopsRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    CategoriesRepository categoriesRepository;
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
    public List<Users> getUsers(){
        try{
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return usersRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @MutationMapping
    @Transactional
    public Map<String, String> createUser(@Argument FormCreateUserInput form){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            String passwordHashed = bCryptPasswordEncoder.encode(form.getPassword());
            Users addUser = new Users(id.toString(), form.getEmail(), passwordHashed, form.getFullName(), form.getAddress(), form.getGender(), form.getPhoneNumber());
            entityManager.persist(addUser);
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

    @MutationMapping
    public Map<String, String> login(@Argument FormLoginInput form){
        HashMap<String, String> map = new HashMap<>();
        try{
            Users userInfo = usersRepository.login(form.getEmail(), StatusCode.Active.getKey());
            if(userInfo == null || !bCryptPasswordEncoder.matches(form.getPassword(), userInfo.getPassword())){
                throw new Error(Errors.UserNotFound.getValue());
            }
            map.put("isSuccess", "true");
            map.put("token", jwtTokenProvider.generateToken(userInfo.getId()));
            map.put("error", null);
        }catch (Error error){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", error.getMessage());
        }
        return map;
    }

    @QueryMapping
    public Users getEmail(@Argument String email){
        try{
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return usersRepository.findByEmail(email);
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }
    @QueryMapping
    public Shops getShopName(@Argument String name){
        try{
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return shopsRepository.findByName(name);
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    public Products gerProductsName(@Argument String name){
        try{
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return productsRepository.findByName(name);
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @QueryMapping
    public List<Shops> getShops(){
        try{
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return shopsRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

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

    @QueryMapping
    public List<Orders> getOrders(){
        try{
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return ordersRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @QueryMapping
    public  List<Categories> getCategories(){
        try {
            if (!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return categoriesRepository.findAll().stream().toList();
        }catch (Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @MutationMapping
    @Transactional
    public Map<String, String> createShops(@Argument FormCreateShopInput shopforms){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            Shops addShops = new Shops(id.toString(), shopforms.getName(), shopforms.getAddress(), shopforms.getPhoneNumber(), shopforms.getLogo(), shopforms.getBanner());
            entityManager.persist(addShops);
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

    @MutationMapping
    @Transactional
    public Map<String, String> createProducts(@Argument FormCreateProductInput productform){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            Products addProducts = new Products(id.toString(), productform.getName(), productform.getDescription(),
            productform.getPrice());
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

    @MutationMapping
    @Transactional
    public Map<String, String> createOrders(@Argument FormCreateOrderInput ordersform){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            Orders addOrders = new Orders(id.toString(), ordersform.getTotalMoney(), ordersform.getQuantity(),
                    ordersform.getDiscount());
            entityManager.persist(addOrders);
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

    @MutationMapping
    @Transactional
    public Map<String, String> createCategories(@Argument FormCreateCategoriesInput categoriesform){
        HashMap<String, String> map = new HashMap<>();

        try {
            Categories addCategories = new Categories( categoriesform.getAlias(), categoriesform.getName(),
                    categoriesform.getDescription());
            entityManager.persist(addCategories);
            map.put("isSuccess", "true");
            map.put("token", jwtTokenProvider.generateToken(categoriesform.alias).toString());
            map.put("error", null);
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }
}

