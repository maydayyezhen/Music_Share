package org.javaclimb.springbootmusic.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.javaclimb.springbootmusic.model.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Integer>{

    UserDetails findByUsername(String username);
    boolean existsByUsername(String username);

    UserDetails findByUsernameAndPassword(String name, String password);
}
