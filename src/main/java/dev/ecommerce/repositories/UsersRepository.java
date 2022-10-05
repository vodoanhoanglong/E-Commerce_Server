package dev.ecommerce.repositories;

import dev.ecommerce.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {
    @Query("SELECT user FROM Users user WHERE user.email = ?1 AND user.status = ?2")
    Users login(String email, String status);
    Users findByEmail(String email);
    Optional<Users> findById(String id);

    @Query("update Users u set u.fullName = ?1, u.password = ?2, u.address =?3, u.phoneNumber = ?4 where u.id= ?5")
    Users setUsersInfoByID(String fullName, String password, String address, String phoneNumber, String id);
}