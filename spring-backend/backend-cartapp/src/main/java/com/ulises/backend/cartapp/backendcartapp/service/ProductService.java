package com.ulises.backend.cartapp.backendcartapp.service;

import java.util.List;
import java.util.Optional;

import com.ulises.backend.cartapp.backendcartapp.entity.Producto;


public interface ProductService {
    
    List<Producto> findAll();

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);

}
