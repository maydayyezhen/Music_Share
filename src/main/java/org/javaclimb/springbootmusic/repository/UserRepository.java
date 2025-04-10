package org.javaclimb.springbootmusic.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.javaclimb.springbootmusic.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUsername(String username);
    boolean existsByUsername(String username);
}
