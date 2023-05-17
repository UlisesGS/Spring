package com.libreria.springboot.backend.apirest.models.dao;

import com.libreria.springboot.backend.apirest.models.entity.Cliente;
import com.libreria.springboot.backend.apirest.models.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IClienteDao extends JpaRepository<Cliente,Long> {

    @Query("from Region")/*PERSONALIZAR CONSULTAS*/
    public List<Region> findAllRegiones();
}
