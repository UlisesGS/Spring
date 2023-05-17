package com.ulises.backend.cartapp.backendcartapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ulises.backend.cartapp.backendcartapp.entity.Producto;
import com.ulises.backend.cartapp.backendcartapp.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return (List<Producto>)productRepository.findAll();
    }
    
}
