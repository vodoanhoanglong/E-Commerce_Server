package dev.ecommerce.resolvers.order.handler;

import dev.ecommerce.models.Orders;
import dev.ecommerce.models.Users;
import dev.ecommerce.repositories.OrdersRepository;
import dev.ecommerce.repositories.ShopsRepository;
import dev.ecommerce.resolvers.auth.schema.FormCreateUserInput;
import dev.ecommerce.resolvers.order.schema.FormCreateOrderInput;
import dev.ecommerce.shared.auth.JwtTokenProvider;
import dev.ecommerce.shared.errors.CustomMessageError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
public class OrderResolver {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ShopsRepository shopsRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @MutationMapping
    @Transactional
    public Map<String, Object> createOrder(GraphQLContext graphQLContext,@Argument @Valid FormCreateOrderInput form){
        try {
            HashMap<String, Object> order = new HashMap<>();
            Users currentUser = graphQLContext.get(Headers.CurrentUser.getValue());
            String shopID = shopsRepository.getShopsByCreatedBy(currentUser.getId()).getId();
            UUID id = UUID.randomUUID();
            Orders addOrder = new Orders(id.toString(), form.getTotalMoney(), form.getDiscount(), form.getQuantity());
            entityManager.persist(addOrder);
            order.put("isSuccess", true);
            order.put("totalMoney", form.getTotalMoney());
            order.put("discount", form.getDiscount());
            order.put("quantity", form.getQuantity());
            return order;
        }catch (Error err){
            throw new CustomMessageError(err.getMessage());
        }
    }

    @QueryMapping
    public List<Orders> getOrders(){
        try{
            return ordersRepository.findAll().stream().toList();
        }catch(Error error){
            throw new CustomMessageError(error.getMessage());
        }
    }
}
