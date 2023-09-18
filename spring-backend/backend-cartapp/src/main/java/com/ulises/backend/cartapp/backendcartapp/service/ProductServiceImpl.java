package com.ulises.backend.cartapp.backendcartapp.service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Producto> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Producto save(Producto usuario) {
    /*Rol rol = rolRepositorio.findByNombre("ROLE_USER");
    usuario.setRoles(Arrays.asList(rol));*/
        return productRepository.save(usuario);
    }

}
