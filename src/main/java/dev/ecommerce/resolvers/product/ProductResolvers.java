package dev.ecommerce.resolvers.product;

import dev.ecommerce.DTO.PaginationInput;
import dev.ecommerce.DTO.product.ProductReqBody;
import dev.ecommerce.models.ProductImages;
import dev.ecommerce.models.Products;
import dev.ecommerce.repositories.ProductImagesRepository;
import dev.ecommerce.repositories.ProductsRepository;
import dev.ecommerce.DTO.PaginationData;
import dev.ecommerce.repositories.ShopsRepository;
import dev.ecommerce.shared.auth.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ProductResolvers {
    @Autowired
    HttpServletRequest request;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ProductImagesRepository productImagesRepository;
    @Autowired
    ShopsRepository shopsRepository;

    @QueryMapping
    public Map<String, Object> getProducts(@Argument PaginationInput paginate) {
        Map<String, Object> response = new HashMap<>();
        int pageNumber, pageSize;
        if (paginate == null) {
            pageNumber = 0;
            pageSize = 5;
        } else {
            pageNumber = paginate.getPage();
            pageSize = paginate.getSize();
        }
        try {
            Pageable paging = PageRequest.of(pageNumber, pageSize);
            Page<Products> pageProds = productsRepository.findAll(paging);
            List<Products> productsList = pageProds.getContent();
            PaginationData pagination = new PaginationData(pageProds.getTotalElements(), pageProds.getNumber(), pageProds.getTotalPages(), pageProds.getSize());
            response.put("data", productsList);
            response.put("pagination", pagination);
        } catch (Error error) {
            response.put("Error", error.getMessage());
        }
        return response;
    }

    @MutationMapping
    public Map<String, Object> createNewProduct(@Argument ProductReqBody product) {
        Map<String, Object> res = new HashMap<>();
        String token = request.getHeader("Authorization").split(" ")[1];
        String userID = jwtTokenProvider.getUserIdFromJWT(token);
        String shopID = shopsRepository.getShopsByCreatedBy(userID).getId();
        try {
            Products duplicate = productsRepository.findProductsByName(product.getName());
            if (duplicate != null) {
                res.put("message", "Sản phầm đã tồn tại!");
            } else {
                UUID id = UUID.randomUUID();
                String name = product.getName();
                String desc = product.getDescription();
                Float price = product.getPrice();
                Products newProduct = productsRepository.save(new Products(String.valueOf(id), name, desc, price, 0L, userID, shopID));
                List<String> imagesUrls = product.getImages();
                if (imagesUrls != null && !imagesUrls.isEmpty()) {
                    List<ProductImages> images = new ArrayList<>();
                    imagesUrls.forEach(url -> {
                        UUID imageID = UUID.randomUUID();
                        ProductImages newImage = productImagesRepository.save(new ProductImages(String.valueOf(imageID), url, String.valueOf(id), String.valueOf(userID)));
                        images.add(newImage);
                    });
                    newProduct.setImages(images);
                }
                res.put("data", newProduct);
                res.put("message", "Successfully!");
            }

        } catch (Error error) {
            res.put("message", error.getMessage());
        }
        return res;
    }
}