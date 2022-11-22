package dev.ecommerce.shared.auth;

import dev.ecommerce.models.Users;
import dev.ecommerce.repositories.UsersRepository;
import dev.ecommerce.shared.auth.JwtTokenProvider;
import dev.ecommerce.shared.resources.Headers;
import dev.ecommerce.shared.resources.Roles;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.*;

@Configuration
public class HeaderInterceptor implements WebGraphQlInterceptor {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public @NotNull Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        final HttpHeaders headers = request.getHeaders();
        Map<String, List<String>> data = new HashMap<>(headers);

        Users currentUser = new Users();
        List<String> authorization = data.get(Headers.Authorization.getKey());

        if(authorization != null && !authorization.get(0).isEmpty()){
            String token = authorization.get(0).split(" ")[1];
            String userId = jwtTokenProvider.getUserIdFromJWT(token);
            currentUser = usersRepository.getUsersById(userId);
        }

        Users finalCurrentUser = currentUser;
        request.configureExecutionInput((executionInput, builder) -> builder.graphQLContext(
                Collections.singletonMap(Headers.CurrentUser.getValue(), finalCurrentUser)).build()
        );
        return chain.next(request);
    }
}
