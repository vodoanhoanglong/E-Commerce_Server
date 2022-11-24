package dev.ecommerce.resolvers.product.handler;

import dev.ecommerce.models.Users;
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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ProductResolver {
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ProductImagesRepository productImagesRepository;
    @Autowired
    ShopsRepository shopsRepository;

    @QueryMapping
    public Map<String, Object> getProducts(@Argument PaginationInput paginate) {
        try {
            Map<String, Object> response = new HashMap<>();

            PaginationInput defaultValue = new PaginationInput(5, 0);
            PaginationInput paginationValue = Data.getValueOrDefault(paginate, defaultValue);

            Pageable paging = PageRequest.of(paginationValue.getPage(), paginationValue.getSize());
            Page<Products> pageProducts = productsRepository.findAll(paging);
            List<Products> productsList = pageProducts.getContent();

            PaginationData pagination = new PaginationData(pageProducts.getTotalElements(), pageProducts.getNumber(), pageProducts.getTotalPages(), pageProducts.getSize());
            response.put(Responses.Data.getKey(), productsList);
            response.put(Responses.Pagination.getKey(), pagination);
            return response;
        } catch (Error error) {
            throw new CustomMessageError(error.getMessage());
        }
    }

    public List<ProductImages> upload_imageList(String product_id, String user_id, List<String> urls) {
        try {
            List<ProductImages> images = new ArrayList<>();
            urls.forEach(url -> {
                UUID imageID = UUID.randomUUID();
                ProductImages newImage = productImagesRepository.save(new ProductImages(String.valueOf(imageID), url, product_id, user_id));
                images.add(newImage);
            });
            return images;
        } catch (Error error) {
            log.error(error.getMessage());
            throw new CustomMessageError(error.getMessage());
        }
    }

    @MutationMapping
    public Map<String, Object> create_product(GraphQLContext graphQLContext, @Argument @Valid ProductReqBody data) {
        try {
            Map<String, Object> res = new HashMap<>();
            Users currentUser = graphQLContext.get(Headers.CurrentUser.getValue());
            String shopID = shopsRepository.getShopsByCreatedBy(currentUser.getId()).getId();
            Products duplicate = productsRepository.findProductsByName(data.getName());
            if (duplicate != null) {
                throw new Error(Errors.ProductAlreadyExist.getValue());
            }
            UUID product_id = UUID.randomUUID();
            String name = data.getName();
            String desc = data.getDescription();
            Float price = data.getPrice();
            Products newProduct = productsRepository.save(new Products(String.valueOf(product_id), name, desc, price, 0L, currentUser.getId(), shopID));
            List<String> imagesUrls = data.getImages();
            if (imagesUrls != null && !imagesUrls.isEmpty()) {
                newProduct.setImages(upload_imageList(String.valueOf(product_id), currentUser.getId(), imagesUrls));
            }
            res.put(Responses.Data.getKey(), newProduct);
            res.put(Responses.Message.getKey(), "Successfully!");
            return res;
        } catch (Error error) {
            log.error(error.getMessage());
            throw new CustomMessageError(error.getMessage());
        }
    }


    @MutationMapping
    public Map<String, Object> update_productQty(@Argument @Valid String product_id, @Argument @Valid Long value) {
        try {
            Map<String, Object> res = new HashMap<>();
            Products targetProduct = productsRepository.getProductsById(product_id);
            targetProduct.setQuantityStore(targetProduct.getQuantityStore() + value);
            Products updatedData = productsRepository.save(targetProduct);
            res.put(Responses.Message.getKey(), "Successfully!");
            res.put(Responses.Data.getKey(), updatedData);
            return res;
        } catch (Error error) {
            log.error(error.getMessage());
            throw new CustomMessageError(error.getMessage());
        }
    }

    @MutationMapping
    public Map<String, Object> update_productInfo(@Argument @Valid String product_id, @Argument @Valid ProductReqBody data) {
        try {
            Map<String, Object> res = new HashMap<>();
            Products targetProduct = productsRepository.getProductsById(product_id);
            targetProduct.setName(data.getName());
            targetProduct.setDescription(data.getDescription());
            targetProduct.setPrice(data.getPrice());
            Products updatedData = productsRepository.save(targetProduct);
            res.put(Responses.Message.getKey(), "Successfully!");
            res.put(Responses.Data.getKey(), updatedData);
            return res;
        } catch (Error error) {
            log.error(error.getMessage());
            throw new CustomMessageError(error.getMessage());
        }
    }

    @MutationMapping
    public Map<String, Object> update_productImg(@Argument @Valid String product_id, @Argument @Valid List<ProductImages> images) {
        try {
            Map<String, Object> res = new HashMap<>();
            Products targetProduct = productsRepository.getProductsById(product_id);
            targetProduct.setImages(images);
            Products updatedData = productsRepository.save(targetProduct);
            res.put(Responses.Message.getKey(), "Successfully!");
            res.put(Responses.Data.getKey(), updatedData);
            return res;
        } catch (Error error) {
            log.error(error.getMessage());
            throw new CustomMessageError(error.getMessage());
        }
    }

    @MutationMapping
    public Map<String, Object> delete_product(@Argument @Valid String product_id) {
        try {
            Map<String, Object> res = new HashMap<>();
            Products target = productsRepository.getProductsById(product_id);
            target.setStatus("disable");
            Products isDisabled = productsRepository.save(target);
            res.put(Responses.Message.getKey(), "Successfully!");
            res.put(Responses.Data.getKey(), isDisabled);
            return res;
        } catch (Error error) {
            log.error(error.getMessage());
            throw new CustomMessageError(error.getMessage());
        }
    }
}