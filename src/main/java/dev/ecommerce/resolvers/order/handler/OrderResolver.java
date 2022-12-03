package dev.ecommerce.resolvers.order.handler;

import dev.ecommerce.models.OrderDetails;
import dev.ecommerce.models.Orders;
import dev.ecommerce.models.Products;
import dev.ecommerce.models.Users;
import dev.ecommerce.repositories.OrderDetailsRepository;
import dev.ecommerce.repositories.OrdersRepository;
import dev.ecommerce.repositories.ProductsRepository;
import dev.ecommerce.resolvers.order.schema.FormCreateOrder;
import dev.ecommerce.resolvers.order.schema.PaymentType;
import dev.ecommerce.shared.errors.CustomMessageError;
import dev.ecommerce.shared.helpers.Data;
import dev.ecommerce.shared.resources.Errors;
import dev.ecommerce.shared.resources.Headers;
import dev.ecommerce.shared.resources.Responses;
import dev.ecommerce.shared.schemas.PaginationData;
import dev.ecommerce.shared.schemas.PaginationInput;
import graphql.GraphQLContext;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class OrderResolver {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    EntityManager entityManager;

    @MutationMapping
    @Transactional
    public String createOrder(GraphQLContext context, @Argument @Valid FormCreateOrder form){
        try {
            List<Products> productsList = productsRepository.findAllProductByIdIn(form.getProductIds());
            if (productsList.size() == 0)
                throw new Error(Errors.ProductNotFound.getValue());

            UUID orderId = UUID.randomUUID();

            float totalMoney = 0;
            for (Products value : productsList) {
                totalMoney += value.getPrice();
            }

            int isPaid = form.getPaymentType().equals(PaymentType.Online.getType()) ? 1 : 0;
            Timestamp currTimestamp = isPaid == 1 ? new Timestamp(new Date().getTime()) : null;
            Users currentUser = context.get(Headers.CurrentUser.getValue());
            Orders order = new Orders(orderId.toString(), totalMoney, productsList.size(), (float) 0,
                   form.getDeliveryAddress() ,isPaid, currTimestamp,  form.getPaymentType(),
                   currentUser.getId());
            ordersRepository.save(order);

            List<OrderDetails> orderDetailsList = new ArrayList<>();
            productsList.forEach(product -> {
                UUID id = UUID.randomUUID();
                OrderDetails orderDetail = new OrderDetails(id.toString(), order.getId(), product.getId(), product.getShopId());
                orderDetailsList.add(orderDetail);
            });

            orderDetailsRepository.saveAll(orderDetailsList);

            return "Create order successfully";
        }catch (Error err){
            throw new CustomMessageError(err.getMessage());
        }
    }

    @QueryMapping
    public Map<String, Object> getListOrders(GraphQLContext context, @Argument PaginationInput paginate) {
        try{
            Users currentUser = context.get(Headers.CurrentUser.getValue());
            PaginationInput defaultValue = new PaginationInput(5, 0);
            PaginationInput paginateInput = paginate != null ?
                    new PaginationInput(paginate.getSize() , paginate.getPage()) : defaultValue;
            PaginationInput paginationValue = Data.getValueOrDefault(paginateInput, defaultValue);

            Pageable paging = PageRequest.of(paginationValue.getPage(), paginationValue.getSize());
            Page<Orders> pageOrder = ordersRepository.findAllByCreatedBy(currentUser.getId(), paging);
            List<Orders> ordersList = pageOrder.getContent();

            PaginationData pagination = new PaginationData(pageOrder.getTotalElements(), pageOrder.getNumber(), pageOrder.getTotalPages(), pageOrder.getSize());
            HashMap<String, Object> response = new HashMap<>();
            response.put(Responses.Data.getKey(), ordersList);
            response.put(Responses.Pagination.getKey(), pagination);
            return response;
        }catch (Error error){
            throw new CustomMessageError(error.getMessage());
        }
    }
}
