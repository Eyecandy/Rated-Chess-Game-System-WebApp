package io.muic.designpattern.repositories;

import io.muic.designpattern.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findUsersByUsernameContains(String keyword);
}
