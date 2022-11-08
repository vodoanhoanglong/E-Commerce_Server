package dev.ecommerce.resolvers.product;

import dev.ecommerce.models.ProductImages;
import dev.ecommerce.models.Products;
import dev.ecommerce.repositories.ProductImagesRepository;
import dev.ecommerce.repositories.ProductsRepository;
import dev.ecommerce.shared.Authentication;
import dev.ecommerce.shared.JwtTokenProvider;
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
public class ProductFunction {
    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ProductImagesRepository productImagesRepository;

    @Autowired
    EntityManager entityManager;

    @MutationMapping
    @Transactional
    public Map<String, String> createProduct(@Argument FormCreateProductInput productsform){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            Products addProduct = new Products(id.toString(), productsform.getName(), productsform.getDescription()
                    , productsform.getPrice(), productsform.getQuantityStore());
            entityManager.persist(addProduct);
            map.put("isSuccess", "true");
            map.put("error", null);
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> createImage(@Argument FormCreateProductImageInput imageform){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            ProductImages addImage = new ProductImages(id.toString(), imageform.getUrl());
            entityManager.persist(addImage);
            map.put("isSuccess", "true");
            map.put("error", null);
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("error", err.getMessage());
        }
        return map;
    }


    @QueryMapping
    public List<Products> getProducts(){
        try{
            return productsRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @QueryMapping
    public List<ProductImages> getImage(){
        try{
            return productImagesRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @MutationMapping
    @Transactional
    public Map<String, String> updateProducts(@Argument FormCreateProductInput productsform){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<Products> productsFromDB = productsRepository.findById(productsform.getId());;
            productsFromDB.get().setName(productsform.name);
            productsFromDB.get().setDescription(productsform.description);
            productsFromDB.get().setPrice(productsform.price);
            productsFromDB.get().setQuantityStore(productsform.quantityStore);
            map.put("name", productsFromDB.get().getName());
            map.put("description", productsFromDB.get().getDescription());
            map.put("price", productsFromDB.get().getPrice().toString());
            map.put("quantityStore", productsFromDB.get().getQuantityStore().toString());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("error", err.getMessage());
        }
        return map;
    }

    @MutationMapping
    @Transactional
    public Map<String, String> updateImage(@Argument FormCreateProductImageInput imageform){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<ProductImages> imagesFromDB = productImagesRepository.findById(imageform.getId());;
            imagesFromDB.get().setUrl(imageform.url);;
            map.put("url", imagesFromDB.get().getUrl());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("error", err.getMessage());
        }
        return map;
    }
}
