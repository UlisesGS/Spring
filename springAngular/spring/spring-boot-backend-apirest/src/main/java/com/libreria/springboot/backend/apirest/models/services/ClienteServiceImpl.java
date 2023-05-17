package com.libreria.springboot.backend.apirest.models.services;

import com.libreria.springboot.backend.apirest.models.dao.IClienteDao;
import com.libreria.springboot.backend.apirest.models.entity.Cliente;
import com.libreria.springboot.backend.apirest.models.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;

    @Override
    @Transactional
    public List<Cliente> findAll() {

        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Override
    @Transactional
    public Cliente findById(Long id) {
             /*SI ENCUENTRA EL ID LO RETORNA Y SI NO RETORNA NULL*/
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {

        return clienteDao.save(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }

    @Override
    @Transactional
    public List<Region> findAllRegiones() {
        return clienteDao.findAllRegiones();
    }
}
