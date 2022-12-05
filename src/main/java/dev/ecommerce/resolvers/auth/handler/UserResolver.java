package dev.ecommerce.resolvers.auth.handler;

import dev.ecommerce.models.Users;
import dev.ecommerce.repositories.UsersRepository;
import dev.ecommerce.resolvers.auth.schema.FormCreateUserInput;
import dev.ecommerce.resolvers.auth.schema.FormLoginInput;
import dev.ecommerce.resolvers.auth.schema.FormUpdatePassword;
import dev.ecommerce.resolvers.auth.schema.FormUpdateUsers;
import dev.ecommerce.shared.auth.JwtTokenProvider;
import dev.ecommerce.shared.errors.CustomMessageError;
import dev.ecommerce.shared.resources.Errors;
import dev.ecommerce.shared.resources.Headers;
import dev.ecommerce.shared.resources.StatusCode;
import graphql.GraphQLContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@Controller
@Slf4j
public class UserResolver {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @QueryMapping
    public List<Users> getUsers(){
        try{
            return usersRepository.findAll().stream().toList();
        }catch(Error error){
            throw new CustomMessageError(error.getMessage());
        }
    }
    @QueryMapping
    public Users getCurrentUser(GraphQLContext contextValue){
        try {
            return contextValue.get(Headers.CurrentUser.getValue());
        } catch (Error error){
            throw new CustomMessageError(error.getMessage());
        }
    }

    @MutationMapping
    @Transactional
    public Map<String, Object> createUser(@Argument @Valid FormCreateUserInput form){
        try {
            HashMap<String, Object> map = new HashMap<>();
            UUID id = UUID.randomUUID();
            String passwordHashed = bCryptPasswordEncoder.encode(form.getPassword());
            Users addUser = new Users(id.toString(), form.getEmail(), passwordHashed, form.getFullName(), form.getAddress(),
                    form.getAvatar(), form.getGender(), form.getBod(), form.getPhoneNumber());
            entityManager.persist(addUser);
            map.put("token", jwtTokenProvider.generateToken(id.toString()));
            return map;
        }catch (Error err){
            throw new CustomMessageError(err.getMessage());
        }
    }

    @MutationMapping
    public Map<String, Object> login(@Argument @Valid FormLoginInput form){
        try{
            HashMap<String, Object> map = new HashMap<>();
            Users user = usersRepository.login(form.getEmail(), StatusCode.Active.getKey());
            if(user == null || !bCryptPasswordEncoder.matches(form.getPassword(), user.getPassword())){
                throw new Error(Errors.UserNotFound.getValue());
            }
            map.put("token", jwtTokenProvider.generateToken(user.getId()));
            return map;
        }catch (Error error){
            throw new CustomMessageError(error.getMessage());
        }
    }

    @MutationMapping
    @Transactional
    public Users updateUsers(GraphQLContext contextValue, @Argument @Valid FormUpdateUsers form){

        try {
            Users currentUser = contextValue.get(Headers.CurrentUser.getValue());
            Users usersFromDB = usersRepository.getUsersById(currentUser.getId());
            usersFromDB.setFullName(form.getFullName());
            usersFromDB.setAddress(form.getAddress());
            usersFromDB.setGender(form.getGender());
            usersFromDB.setPhoneNumber(form.getPhoneNumber());
            usersFromDB.setBod(form.getBod());
            usersFromDB.setAvatar(form.getAvatar());
            usersRepository.save(usersFromDB);
            return usersFromDB;
        }catch (Error err){
            throw new CustomMessageError(err.getMessage());
        }
    }

    @MutationMapping
    public Users updatePasswordUsers(GraphQLContext contextValue, @Argument @Valid FormUpdatePassword form){
        try{
            Users currentUser = contextValue.get(Headers.CurrentUser.getValue());
            if(!bCryptPasswordEncoder.matches(form.getPassword(), currentUser.getPassword())){
                throw new Error(Errors.OldPasswordNotCorrect.getValue());
            }
            String newPassword = bCryptPasswordEncoder.encode(form.getNewPassword());
            currentUser.setPassword(newPassword);
            usersRepository.save(currentUser);
            return contextValue.get(Headers.CurrentUser.getValue());
        }catch (Error error){
            throw new CustomMessageError(error.getMessage());
        }
    }
}

