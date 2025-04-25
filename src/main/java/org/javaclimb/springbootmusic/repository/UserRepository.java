package org.javaclimb.springbootmusic.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.javaclimb.springbootmusic.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);

    UserEntity findByUsernameAndPassword(String name, String password);
}
