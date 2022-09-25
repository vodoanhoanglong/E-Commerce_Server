package dev.ecommerce.resolvers.auth;

import dev.ecommerce.models.Shops;
import dev.ecommerce.models.Users;
import dev.ecommerce.repositories.ShopsRepository;
import dev.ecommerce.repositories.UsersRepository;
import dev.ecommerce.shared.Authentication;
import dev.ecommerce.shared.JwtTokenProvider;
import dev.ecommerce.shared.resources.Errors;
import dev.ecommerce.shared.resources.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserResolver {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ShopsRepository shopsRepository;
    @Autowired
    EntityManager entityManager;

    @Autowired
    HttpServletRequest request;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    Authentication auth;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @QueryMapping
    public List<Users> getUsers(){
        try{
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return usersRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @MutationMapping
    @Transactional
    public Map<String, String> createUser(@Argument FormCreateUserInput form){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            String passwordHashed = bCryptPasswordEncoder.encode(form.getPassword());
            Users addUser = new Users(id.toString(), form.getEmail(), passwordHashed, form.getFullName());
            entityManager.persist(addUser);
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
    public Map<String, String> login(@Argument FormLoginInput form){
        HashMap<String, String> map = new HashMap<>();
        try{
            Users userInfo = usersRepository.login(form.getEmail(), StatusCode.Active.getKey());
            if(userInfo == null || !bCryptPasswordEncoder.matches(form.getPassword(), userInfo.getPassword())){
                throw new Error(Errors.UserNotFound.getValue());
            }
            map.put("isSuccess", "true");
            map.put("token", jwtTokenProvider.generateToken(userInfo.getId()));
            map.put("error", null);
        }catch (Error error){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", error.getMessage());
        }
        return map;
    }

    @QueryMapping
    public Users getEmail(@Argument String email){
        try{
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return usersRepository.findByEmail(email);
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }
    @QueryMapping
    public Shops getName(@Argument String name){
        try{
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return shopsRepository.findByName(name);
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }

    @QueryMapping
    public List<Shops> getShops(){
        try{
            if(!auth.isAuthenticated(request)){
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
    public Map<String, String> createShops(@Argument FormCreateShopInput shopforms){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            Shops addShops = new Shops(id.toString(), shopforms.getName(), shopforms.getAddress(), shopforms.getPhoneNumber());
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

