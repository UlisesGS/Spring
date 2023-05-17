/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.servicio;

import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Libro;
import com.example.demo.excepciones.MiException;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.LibroRepository;
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
public class LibroServicio {
    
    @Autowired
    LibroRepository libroR;
    
    @Autowired
    AutorRepository autorR;
    
    
    
    @Transactional
    public void crearLibro(String nombre, Long idAutor) throws MiException{
        
        validarLibro(nombre, idAutor);
        
        Libro libro= new Libro();
        Autor autor= new Autor();
        
        libro.setNombre(nombre);
        
        Optional<Autor> respuesta= autorR.findById(idAutor);
        
        //PONER EL SAVE DENTRO DEL IF
        if (respuesta.isPresent()) {
            autor=respuesta.get();
            libro.setAutor(autor);
        
            libroR.save(libro);
        }
        
    }
    
    
    public List<Libro> listarLibro(){
    
        return libroR.findAll();
    
    }
    
    
    public Libro buscarLibro(Long id){
        
        Libro libro= new Libro();
        
        Optional<Libro> respuesta=libroR.findById(id);
        
        if (respuesta.isPresent()) {
            libro=respuesta.get();
        }
        
        return libro;
    }
    
    
    @Transactional
    public void modificarLirbro(Long id, String nombre, Long idAutor) throws MiException{
        
        validarLibro(nombre, idAutor);
        
        Libro libro= new Libro();
        Autor autor= new Autor();
        
        Optional<Autor> respuestaA= autorR.findById(idAutor);
        
        if (respuestaA.isPresent()) {
            autor=respuestaA.get();
        }
        
        Optional<Libro> respuestaL= libroR.findById(id);
        
        //AGREGAR EL SAVE
        if (respuestaL.isPresent()) {
            libro=respuestaL.get();
            libro.setNombre(nombre);
            libro.setAutor(autor);
            libroR.save(libro);
        }
    }
    
    
    @Transactional
    public void eliminarLibro(Long id){
        
        libroR.deleteById(id);
        
    }
    
    
    public Libro getOne(Long id){
        
        return libroR.getOne(id);
    
    }
    
    
    public void validarLibro(String nombre, Long idAutor) throws MiException{
    
        if (nombre.isEmpty() || nombre==null) {
            throw new MiException("Error, valor no valido.");
        }
        
        if (idAutor == null ) {
            throw new MiException("Error, valor no valido.");
        }
    
    }
}
