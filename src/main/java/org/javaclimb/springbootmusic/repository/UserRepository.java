package org.javaclimb.springbootmusic.repository;
import org.javaclimb.springbootmusic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUsername(String username);
    boolean existsByUsername(String username);
}
