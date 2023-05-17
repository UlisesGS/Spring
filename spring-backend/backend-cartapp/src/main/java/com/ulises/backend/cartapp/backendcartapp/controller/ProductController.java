package com.ulises.backend.cartapp.backendcartapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulises.backend.cartapp.backendcartapp.service.ProductService;

@RestController
@RequestMapping("/cartapp")
public class ProductController {
    
    @Autowired
    private ProductService productService;
 
    @GetMapping("/productos")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(productService.findAll());
    }
}
