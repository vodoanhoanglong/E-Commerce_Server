package dev.ecommerce.resolvers.shops.handler;

import dev.ecommerce.models.Shops;
import dev.ecommerce.repositories.ShopsRepository;
import dev.ecommerce.resolvers.shops.schema.FormCreateShopsInput;
import dev.ecommerce.shared.errors.CustomMessageError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@Controller
@Slf4j
public class ShopsResolver {
    @Autowired
    ShopsRepository shopsRepository;
    @Autowired
    EntityManager entityManager;


    @QueryMapping
    public List<Shops> getShops(){
        try {
            return shopsRepository.findAll().stream().toList();
        } catch (Error error){
            throw new CustomMessageError(error.getMessage());
        }
    }
    @MutationMapping
    @Transactional
    public Map<String, Object> createShops(@Argument @Valid FormCreateShopsInput form){
        try {
            HashMap<String, Object> res = new HashMap<>();
            UUID id = UUID.randomUUID();
            Shops addShop = new Shops(id.toString(),form.getName(), form.getAddress(),form.getPhoneNumber() , form.getLogo(),form.getBanner(),form.getStatus());
            entityManager.persist(addShop);
            res.put("isSuccess", true);
            return res;
        }catch (Error err){
            throw new CustomMessageError(err.getMessage());
        }

    }
//    @MutationMapping
//    @Transactional
//    public Map<String, String> updateShop(@Argument @Valid FormUpdateShops form){
//        HashMap<String, String> res = new HashMap<>();
//
//        try {
//            Shops shopsFromDB = shopsRepository.findByName(form.getName());
//            shopsFromDB.setName(form.getName());
//            shopsFromDB.setAddress(form.getAddress());
//            shopsFromDB.setPhoneNumber(form.getPhoneNumber());
//            shopsFromDB.setLogo(form.getLogo());
//            shopsFromDB.setBanner(form.getBanner());
//            shopsFromDB.setStatus(form.getStatus());
//            res.put("name", shopsFromDB.getName());
//            res.put("Address", shopsFromDB.getAddress());
//            res.put("PhoneNumber", shopsFromDB.getPhoneNumber());
//            res.put("Logo", shopsFromDB.getLogo());
//            res.put("Banner", shopsFromDB.getBanner());
//            res.put(("Status"), shopsFromDB.getStatus());
//            return res;
//        }catch (Error err){
//            throw new CustomMessageError(err.getMessage());
//        }
//    }

}
