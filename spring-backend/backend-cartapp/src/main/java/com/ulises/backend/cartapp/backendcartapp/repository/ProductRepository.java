package com.ulises.backend.cartapp.backendcartapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ulises.backend.cartapp.backendcartapp.entity.Producto;

public interface ProductRepository extends JpaRepository<Producto, Long> {
    
}
