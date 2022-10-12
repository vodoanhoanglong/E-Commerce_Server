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
import java.util.*;

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
    ProductImagesRepository productImagesRepository;
    @Autowired
    OrderDetailsRepository orderDetailsRepository;
    @Autowired
    CategoryProductsRepository categoryProductsRepository;
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

    @QueryMapping
    public  List<ProductImages> getImage(){
        try {
            if (!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return productImagesRepository.findAll().stream().toList();
        }catch (Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @QueryMapping
    public  List<CategoryProducts> getCategoryProduct(){
        try {
            if (!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return categoryProductsRepository.findAll().stream().toList();
        }catch (Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @QueryMapping
    public  List<OrderDetails> getDetails(){
        try {
            if (!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return orderDetailsRepository.findAll().stream().toList();
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
            Shops addShops = new Shops(id.toString(), shopforms.getName(), shopforms.getAddress(), shopforms.getPhoneNumber()
                    , shopforms.getLogo(), shopforms.getBanner());
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

    @MutationMapping
    @Transactional
    public Map<String, String> createProductImage(@Argument FormCreateProductImageInput imageforms){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            ProductImages addImage = new ProductImages(id.toString(), imageforms.getUrl());
            entityManager.persist(addImage);
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
    public Map<String, String> createCategoryProduct(@Argument FormCreateCategoryProductInput categoryproductforms){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            CategoryProducts addCategoryProduct = new CategoryProducts(id.toString(), categoryproductforms.getCategoryAlias(), categoryproductforms.getProductId());
            entityManager.persist(addCategoryProduct);
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
    public Map<String, String> createOrderDetails(@Argument FormCreateOrderDetailsInput detailsforms){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            OrderDetails addDetails = new OrderDetails(id.toString(), detailsforms.getOrderId(), detailsforms.getProductId());
            entityManager.persist(addDetails);
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
    public Map<String, String> updateUser(@Argument FormCreateUserInput form){
        HashMap<String, String> map = new HashMap<>();

        try {
           Optional<Users> usersFromDB = usersRepository.findById(form.getId());
           System.out.println("||||||||\t" + usersFromDB.get().getFullName());
           usersFromDB.get().setFullName(form.fullName);
           usersFromDB.get().setAddress(form.address);
           usersFromDB.get().setPassword(form.password);
           usersFromDB.get().setPhoneNumber(form.phoneNumber);
           usersRepository.save(usersFromDB.get());
           map.put("fullName", usersFromDB.get().getFullName());
           map.put("address", usersFromDB.get().getAddress());
           map.put("password", usersFromDB.get().getPassword());
           map.put("phoneNumber", usersFromDB.get().getPhoneNumber());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> updateShops(@Argument FormCreateShopInput shopforms){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<Shops> shopFromDB = shopsRepository.findById(shopforms.getId());
            shopFromDB.get().setName(shopforms.name);
            shopFromDB.get().setAddress(shopforms.address);
            shopFromDB.get().setPhoneNumber(shopforms.phoneNumber);
            shopsRepository.save(shopFromDB.get());
            map.put("name", shopFromDB.get().getName());
            map.put("address", shopFromDB.get().getAddress());
            map.put("phoneNumber", shopFromDB.get().getPhoneNumber());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> updateProducts(@Argument FormCreateProductInput productform){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<Products> productsFromDB = productsRepository.findById(productform.getId());
            productsFromDB.get().setName(productform.name);
            productsFromDB.get().setPrice(productform.price);
            productsFromDB.get().setDescription(productform.description);
            productsRepository.save(productsFromDB.get());
            map.put("name", productsFromDB.get().getName());
            map.put("description", productsFromDB.get().getDescription());
            map.put("price", productsFromDB.get().getPrice().toString());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> updateCategories(@Argument FormCreateCategoriesInput categoriesform){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<Categories> categoriesFromDB = categoriesRepository.findById(categoriesform.getAlias());
            categoriesFromDB.get().setName(categoriesform.name);
            categoriesFromDB.get().setDescription(categoriesform.description);
            categoriesRepository.save(categoriesFromDB.get());
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
    public Map<String, String> updateImages(@Argument FormCreateProductImageInput imageforms){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<ProductImages> imagesFromDB = productImagesRepository.findById(imageforms.getId());
            imagesFromDB.get().setUrl(imageforms.url);
            productImagesRepository.save(imagesFromDB.get());
            map.put("url", imagesFromDB.get().getUrl());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> deleteUser(@Argument FormCreateUserInput form){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<Users> usersFromDB = usersRepository.findById(form.getId());
            usersRepository.delete(usersFromDB.get());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> deleteShops(@Argument FormCreateShopInput shopforms){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<Shops> shopFromDB = shopsRepository.findById(shopforms.getId());
            shopsRepository.delete(shopFromDB.get());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> deleteProducts(@Argument FormCreateProductInput productform){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<Products> productsFromDB = productsRepository.findById(productform.getId());
            productsRepository.delete(productsFromDB.get());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> deleteCategories(@Argument FormCreateCategoriesInput categoriesform){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<Categories> categoriesFromDB = categoriesRepository.findById(categoriesform.getAlias());
            categoriesRepository.delete(categoriesFromDB.get());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> deleteImages(@Argument FormCreateProductImageInput imageforms){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<ProductImages> imagesFromDB = productImagesRepository.findById(imageforms.getId());
            productImagesRepository.delete(imagesFromDB.get());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }
}

