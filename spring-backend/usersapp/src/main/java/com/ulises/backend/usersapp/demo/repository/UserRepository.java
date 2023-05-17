package com.ulises.backend.usersapp.demo.repository;

import com.ulises.backend.usersapp.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
