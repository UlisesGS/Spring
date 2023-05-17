/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.servicio;

import com.example.demo.entidades.Autor;
import com.example.demo.excepciones.MiException;
import com.example.demo.repository.AutorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ulises
 */
@Service
public class AutorServicio {
    
    @Autowired
    AutorRepository autorR;
    
    
    @Transactional
    public void crearAutor(String nombre) throws MiException{
        
        validarAutor(nombre);
        
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        
        autorR.save(autor);
    
    }
    
    
    public List<Autor> listarAutor(){
        
        List<Autor>autores=new ArrayList();
        
        autores= autorR.findAll();
        
        return autores;
    }
    
    
    public Autor buscarAutorId(Long id){
    
        Autor autor= new Autor();
        
        Optional<Autor> respuesta=autorR.findById(id);
        
        if (respuesta.isPresent()) {
            autor=respuesta.get();
        }
        
        return autor;
    
    }
    
    
    @Transactional
    public void modificarAutor(Long id, String nombre) throws MiException{
        
        validarAutor(nombre);
        
        Autor autor = new Autor();
        
        Optional<Autor> respuesta=autorR.findById(id);
        
        if (respuesta.isPresent()) {
            autor=respuesta.get();
            autor.setNombre(nombre);
            autorR.save(autor);
        }
    
    }
    
    
    @Transactional
    public void eliminarAutor(Long id){
    
        autorR.deleteById(id);
    
    }
    
    
    public Autor getOne(Long id){
    
        return autorR.getOne(id);
    
    } 
    
    
    public void validarAutor(String nombre) throws MiException{
    
        if (nombre.isEmpty() || nombre==null) {
            throw new MiException("Error, no puede ser nulo.");
        }
    
    }
}
