package com.webrise.SpringMicroservice.repository;


import com.webrise.SpringMicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}