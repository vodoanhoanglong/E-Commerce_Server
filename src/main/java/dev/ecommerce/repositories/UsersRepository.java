package dev.ecommerce.repositories;

import dev.ecommerce.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {
    @Query("SELECT user FROM Users user WHERE user.email = ?1 AND user.status = ?2")
    Users login(String email, String status);
    Users getUsersById(String id);
}