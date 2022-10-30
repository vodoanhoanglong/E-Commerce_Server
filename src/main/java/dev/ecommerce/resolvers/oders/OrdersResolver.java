package dev.ecommerce.resolvers.oders;

import dev.ecommerce.models.OrderDetails;
import dev.ecommerce.models.Orders;
import dev.ecommerce.models.Products;
import dev.ecommerce.repositories.OrderDetailsRepository;
import dev.ecommerce.repositories.OrdersRepository;
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
public class OrdersResolver {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    HttpServletRequest request;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    Authentication oder;

    @QueryMapping
    public List<Orders> getOders(){
        try{
            if(!oder.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return ordersRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }
    @QueryMapping
    public List<OrderDetails> getOdersDetails(){
        try{
            if(!oder.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return orderDetailsRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }
    @MutationMapping
    @Transactional
    public Map<String, String> createOrders(@Argument FormCreateOrders ordersForm){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            Orders addOders = new Orders(id.toString(), ordersForm.getTotalMoney(), ordersForm.getQuantity(), ordersForm.getDiscount(),ordersForm.getStatus(),ordersForm.getShopId());
            entityManager.persist(addOders);
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
    public Map<String, String> createOrdersDetails(@Argument FromCreateOrdersDetails ordersDetailsForm){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            OrderDetails addOderDetails = new OrderDetails(id.toString(), ordersDetailsForm.getOrderId(), ordersDetailsForm.getProductId(), ordersDetailsForm.getStatus());
            entityManager.persist(addOderDetails);
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
