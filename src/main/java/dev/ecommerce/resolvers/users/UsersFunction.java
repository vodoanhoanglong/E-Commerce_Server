package dev.ecommerce.resolvers.users;

import dev.ecommerce.models.Users;
import dev.ecommerce.repositories.UsersRepository;
import dev.ecommerce.resolvers.auth.FormCreateUserInput;
import dev.ecommerce.shared.Authentication;
import dev.ecommerce.shared.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
public class UsersFunction {
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


    @MutationMapping
    @Transactional
    public Map<String, String> updateUser(@Argument FormCreateUserInput form){
        HashMap<String, String> map = new HashMap<>();

        try {
            Optional<Users> usersFromDB = usersRepository.findById(form.getId());
            System.out.println("||||||||\t" + usersFromDB.get().getFullName());
            usersFromDB.get().setFullName(form.fullName);
            usersFromDB.get().setAddress(form.address);
            usersFromDB.get().setPassword(form.password);
            usersFromDB.get().setPhoneNumber(form.phoneNumber);
            usersFromDB.get().setAvatar(form.avatar);
            usersFromDB.get().setBod(form.bod);
            usersRepository.save(usersFromDB.get());
            map.put("fullName", usersFromDB.get().getFullName());
            map.put("address", usersFromDB.get().getAddress());
            map.put("password", usersFromDB.get().getPassword());
            map.put("phoneNumber", usersFromDB.get().getPhoneNumber());
            map.put("avatar", usersFromDB.get().getAvatar());
            map.put("bod", usersFromDB.get().getBod());
        }catch (Error err){
            map.put("isSuccess", "false");
            map.put("token", null);
            map.put("error", err.getMessage());
        }
        return map;
    }
}
