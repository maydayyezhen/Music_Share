package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin findByUsername(String username);
}
