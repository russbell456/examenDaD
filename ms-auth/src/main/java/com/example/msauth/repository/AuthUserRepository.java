package com.example.msauth.repository;


import com.example.msauth.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthUserRepository extends JpaRepository<AuthUser,Integer> {
    AuthUser findByUsername(String username);
}
