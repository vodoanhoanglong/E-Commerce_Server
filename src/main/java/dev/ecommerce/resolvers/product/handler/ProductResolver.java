package dev.ecommerce.resolvers.product.handler;

import dev.ecommerce.models.Orders;
import dev.ecommerce.models.Users;
import dev.ecommerce.resolvers.product.schema.FilterProductList;
import dev.ecommerce.shared.helpers.Data;
import dev.ecommerce.shared.resources.Responses;
import dev.ecommerce.shared.schemas.PaginationInput;
import dev.ecommerce.resolvers.product.schema.ProductReqBody;
import dev.ecommerce.models.ProductImages;
import dev.ecommerce.models.Products;
import dev.ecommerce.shared.errors.CustomMessageError;
import dev.ecommerce.repositories.ProductImagesRepository;
import dev.ecommerce.repositories.ProductsRepository;
import dev.ecommerce.shared.schemas.PaginationData;
import dev.ecommerce.repositories.ShopsRepository;
import dev.ecommerce.shared.resources.Errors;
import dev.ecommerce.shared.resources.Headers;
import graphql.GraphQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.*;

@Controller
public class ProductResolver {
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ProductImagesRepository productImagesRepository;
    @Autowired
    ShopsRepository shopsRepository;

    @QueryMapping
    public Map<String, Object> getProducts(@Argument FilterProductList filter) {
        try {
            Map<String, Object> response = new HashMap<>();

            PaginationInput defaultValue = new PaginationInput(5, 0);
            PaginationInput paginationValue = Data.getValueOrDefault(filter.getPaginate(), defaultValue);

            Pageable paging = PageRequest.of(paginationValue.getPage(), paginationValue.getSize());
            Page<Products> pageProducts = filter.getCategoryAliases().size() > 0 ?
                    productsRepository.findAllByCategoryAliasIn(filter.getCategoryAliases(), paging)
                    : productsRepository.findAll(paging);
            List<Products> productsList = pageProducts.getContent();

            PaginationData pagination = new PaginationData(pageProducts.getTotalElements(), pageProducts.getNumber(), pageProducts.getTotalPages(), pageProducts.getSize());
            response.put(Responses.Data.getKey(), productsList);
            response.put(Responses.Pagination.getKey(), pagination);
            return response;
        } catch (Error error) {
            throw new CustomMessageError(error.getMessage());
        }
    }

    @MutationMapping
    public Map<String, Object> createNewProduct(GraphQLContext graphQLContext, @Argument @Valid ProductReqBody product) {
        try {
            Map<String, Object> res = new HashMap<>();
            Users currentUser = graphQLContext.get(Headers.CurrentUser.getValue());
            String shopID = shopsRepository.getShopsByCreatedBy(currentUser.getId()).getId();
            Products duplicate = productsRepository.findProductsByName(product.getName());
            if (duplicate != null) {
                throw new Error(Errors.ProductAlreadyExist.getValue());
            }
            UUID id = UUID.randomUUID();
            String name = product.getName();
            String desc = product.getDescription();
            Float price = product.getPrice();
            Products newProduct = productsRepository.save(new Products(String.valueOf(id), name, desc, price, 0L, currentUser.getId(), shopID));
            List<String> imagesUrls = product.getImages();
            if (imagesUrls != null && !imagesUrls.isEmpty()) {
                List<ProductImages> images = new ArrayList<>();
                imagesUrls.forEach(url -> {
                    UUID imageID = UUID.randomUUID();
                    ProductImages newImage = productImagesRepository.save(new ProductImages(String.valueOf(imageID), url, String.valueOf(id), currentUser.getId()));
                    images.add(newImage);
                });
                newProduct.setImages(images);
            }
            res.put(Responses.Data.getKey(), newProduct);
            res.put(Responses.Message.getKey(), "Successfully!");
            return res;
        } catch (Error error) {
            throw new CustomMessageError(error.getMessage());
        }
    }

}