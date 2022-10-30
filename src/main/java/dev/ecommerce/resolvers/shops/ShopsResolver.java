package dev.ecommerce.resolvers.shops;

import dev.ecommerce.models.Orders;
import dev.ecommerce.models.Shops;
import dev.ecommerce.repositories.ShopsRepository;
import dev.ecommerce.resolvers.oders.FormCreateOrders;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
public class ShopsResolver {
    @Autowired
    private ShopsRepository shopsRepository;
    @Autowired
    EntityManager entityManager;

    @Autowired
    HttpServletRequest request;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    Authentication shop;

    @QueryMapping
    public List<Shops> getShops(){
        try{
            if(!shop.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return shopsRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }
    @MutationMapping
    @Transactional
    public Map<String, String> createShops(@Argument FormCreateShops shopsForm){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            Shops addShops = new Shops(id.toString(), shopsForm.getName(), shopsForm.getAddress(), shopsForm.getPhoneNumber(),shopsForm.getLogo(),shopsForm.getBanner(), shopsForm.getStatus());
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
}
