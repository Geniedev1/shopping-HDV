package com.example.demo.repository;
import com.example.demo.model.User;
import com.example.demo.model.Role;
import java.util.List;
import com.example.demo.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(Role role);
    List<User> findByStatus(UserStatus status);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

}