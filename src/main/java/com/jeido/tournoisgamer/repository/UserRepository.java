package com.jeido.tournoisgamer.repository;
import com.jeido.tournoisgamer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.jeido.tournoisgamer.utils.Role;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
    User findByEmail (String email);
    List<User> findByNameContainingIgnoreCase(String name);
    List<User> findByRole (Role role);
    void deleteByName(String name);
    boolean existsByName(String name);
    boolean existsByEmail (String email);

}
