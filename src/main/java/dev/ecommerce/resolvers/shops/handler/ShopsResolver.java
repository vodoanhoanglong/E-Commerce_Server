package dev.ecommerce.resolvers.shops.handler;

import dev.ecommerce.models.Shops;
import dev.ecommerce.models.Users;
import dev.ecommerce.repositories.ShopsRepository;
import dev.ecommerce.resolvers.shops.schema.FormCreateShop;
import dev.ecommerce.resolvers.shops.schema.FormUpdateShop;
import dev.ecommerce.shared.errors.CustomMessageError;
import dev.ecommerce.shared.resources.Errors;
import dev.ecommerce.shared.resources.Headers;
import graphql.GraphQLContext;
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
    public List<Shops> getShopsListOfOwner(GraphQLContext context){
        try {
            Users currentUser = context.get(Headers.CurrentUser.getValue());
            return shopsRepository.findAllByCreatedBy(currentUser.getId());
        } catch (Error error){
            throw new CustomMessageError(error.getMessage());
        }
    }
    @MutationMapping
    @Transactional
    public String createShop(@Argument @Valid FormCreateShop form, GraphQLContext context){
        try {
            UUID id = UUID.randomUUID();
            Users currentUser = context.get(Headers.CurrentUser.getValue());
            Shops newShop = new Shops(id.toString(), form.getName(), form.getAddress(),form.getPhoneNumber(),
                    form.getLogo(), form.getBanner(), currentUser.getId());
            shopsRepository.save(newShop);
            return "Create shop successfully";
        }catch (Error err){
            throw new CustomMessageError(err.getMessage());
        }

    }

    @MutationMapping
    @Transactional
    public String updateShop(@Argument FormUpdateShop form, GraphQLContext context){
        try{
            Users currentUser = context.get(Headers.CurrentUser.getValue());
            Shops shopOfOwner = shopsRepository.getShopsByCreatedBy(currentUser.getId());
            if (shopOfOwner == null)
                throw new Error(Errors.ShopNotFound.getValue());

            if (form.getName() != null)
                shopOfOwner.setName(form.getName());
            if(form.getAddress() != null)
                shopOfOwner.setAddress(form.getAddress());
            if(form.getBanner() != null)
                shopOfOwner.setBanner(form.getBanner());
            if(form.getLogo() != null)
                shopOfOwner.setLogo(form.getLogo());
            if(form.getPhoneNumber() != null)
                shopOfOwner.setPhoneNumber(form.getPhoneNumber());

            shopsRepository.save(shopOfOwner);
            return "Update shop successfully";
        }catch (Error error){
            throw new CustomMessageError(error.getMessage());
        }
    }
}
