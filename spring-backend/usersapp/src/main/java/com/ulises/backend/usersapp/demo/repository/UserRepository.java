package com.ulises.backend.usersapp.demo.repository;

import com.ulises.backend.usersapp.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    /*@Query("slect u from User u where u.username=?1") SEGUNDA FORMA
    Optional<User> getUserByUsername(String username)*/
}
