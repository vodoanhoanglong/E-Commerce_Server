package dev.ecommerce.resolvers.auth;

import dev.ecommerce.models.Users;
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
import java.util.*;

@Controller
@Slf4j
public class UserResolver {
    @Autowired
    UsersRepository usersRepository;

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
            // validate user
            if(!auth.isAuthenticated(request)){
                throw new Error(Errors.PermissionDenied.getValue());
            }
            return usersRepository.findAll().stream().toList();
        }catch(Error error){
            log.error(error.getMessage());
            return null;
        }
    }
    @QueryMapping
    public Users getCurrentUser(){
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            String ID = jwtTokenProvider.getUserIdFromJWT(token);
            return  usersRepository.getUsersById(ID);
        } catch (Error error){
            log.error(error.getMessage());
        }
        return null;
    }



    @MutationMapping
    @Transactional
    public Map<String, String> createUser(@Argument FormCreateUserInput form){
        HashMap<String, String> map = new HashMap<>();

        try {
            UUID id = UUID.randomUUID();
            String passwordHashed = bCryptPasswordEncoder.encode(form.getPassword());
            Users addUser = new Users(id.toString(), form.getEmail(), passwordHashed, form.getFullName(), form.getAddress(),
                    form.getAvatar(), form.getBod(), form.getGender(), form.getPhoneNumber(), form.getStatus());
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
            Users user = usersRepository.login(form.getEmail(), StatusCode.Active.getKey());
            if(user == null || !bCryptPasswordEncoder.matches(form.getPassword(), user.getPassword())){
                throw new Error(Errors.UserNotFound.getValue());
            }
            map.put("isSuccess", "true");
            map.put("token", jwtTokenProvider.generateToken(user.getId()));
            map.put("error", null);
        }catch (Error error){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", error.getMessage());
        }
        return map;
    }
}

