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
            List<String> productIds = new ArrayList<>();
            for (FormCreateOrder.ProductOrder product : form.getProducts()) {
                productIds.add(product.getProductId());
            }
            List<Products> productsList = productsRepository.findAllProductByIdIn(productIds);
            if (productsList.size() == 0)
                throw new Error(Errors.ProductNotFound.getValue());

            for (Products product : productsList) {
                for (int j = 0; j < form.getProducts().size(); j++) {
                    if (Objects.equals(product.getId(), form.getProducts().get(j).getProductId())) {
                        if (product.getQuantityStore() < form.getProducts().get(j).getQuantity()) {
                            throw new Error(Errors.ProductNotEnoughOnStore.getValue());
                        }
                        form.getProducts().get(j).setShopId(
                                product.getShopId()
                        );
                        form.getProducts().get(j).setPrice(
                                product.getPrice()
                        );
                    }
                }
            }

            String orderId = UUID.randomUUID().toString();

            float totalMoney = 0;
            int totalQuantity = 0;
            for (FormCreateOrder.ProductOrder value : form.getProducts()) {
                totalMoney += value.getPrice() * value.getQuantity();
                totalQuantity += value.getQuantity();
            }

            int isPaid = form.getPaymentType().equals(PaymentType.Online.getType()) ? 1 : 0;
            Timestamp currTimestamp = isPaid == 1 ? new Timestamp(new Date().getTime()) : null;
            Users currentUser = context.get(Headers.CurrentUser.getValue());
            Orders order = new Orders(orderId, totalMoney - form.getShipCost(), totalQuantity, (float) 0,
                   form.getDeliveryAddress() ,isPaid, currTimestamp,  form.getPaymentType(),
                   currentUser.getId(), form.getShipCost());
            ordersRepository.save(order);

            List<OrderDetails> orderDetailsList = new ArrayList<>();
            form.getProducts().forEach(product -> {
                UUID id = UUID.randomUUID();

                OrderDetails orderDetail = new OrderDetails(id.toString(), order.getId(), product.getProductId(),
                        product.getShopId(), product.getQuantity());

                productsList.forEach(productDb -> {
                    if(Objects.equals(productDb.getId(), product.getProductId())){
                        productDb.setQuantityStore(productDb.getQuantityStore() - product.getQuantity());
                    }
                });
                orderDetailsList.add(orderDetail);
            });

            orderDetailsRepository.saveAll(orderDetailsList);
            productsRepository.saveAllAndFlush(productsList);

            return orderId;
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
