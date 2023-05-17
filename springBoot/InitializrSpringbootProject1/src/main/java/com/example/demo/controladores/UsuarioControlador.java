/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Libro;
import com.example.demo.servicio.AutorServicio;
import com.example.demo.servicio.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ulises
 */
@Controller
@RequestMapping("/user")
public class UsuarioControlador {
    
    @Autowired
    LibroServicio libroR;
    
    @Autowired
    AutorServicio autorR;
    
    @GetMapping("/usuario")
    public String indexUser(){
        return "index.html";
    }
    
    @GetMapping("/user_autor")
    public String userAutor(ModelMap modelo){
        
        List <Autor> autores = autorR.listarAutor();
        
        modelo.addAttribute("autores",autores);
        
        return "/user_autor";
    }
    
    
    @GetMapping("/user_libro")
    public String userLibro(ModelMap modelo){
        
        List<Libro> libros = libroR.listarLibro();
        
        modelo.addAttribute("libros", libros);
        
        return "/user_libro";
    }
    
    
//    @GetMapping("/listaAutor")
//    public String listarAutor(ModelMap modelo){
//    
//        List <Autor> autores = autorR.listarAutor();
//        
//        modelo.addAttribute("autores",autores);
//        
//        return "/user_autorl";
//        
//    }
    
    
//    @GetMapping("/listaLibro")
//    public String listarLibro(ModelMap modelo){
//    
//        List<Libro> libros = libroR.listarLibro();
//        
//        modelo.addAttribute("libros", libros);
//        
//        return "/user_libro";
//    
//    }
}
