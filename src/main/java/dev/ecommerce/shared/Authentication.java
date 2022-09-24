package dev.ecommerce.shared;

import dev.ecommerce.shared.resources.Headers;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class Authentication {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public boolean isAuthenticated(@NotNull HttpServletRequest request){
        String token = request.getHeader(Headers.Authorization.getValue());
        if(token == null){
            return false;
        }
        String tokenSplit = token.split(" ")[1];

        return jwtTokenProvider.validateToken(tokenSplit);
    }
}
