/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Libro;
import com.example.demo.excepciones.MiException;
import com.example.demo.servicio.AutorServicio;
import com.example.demo.servicio.LibroServicio;
import java.util.Collections;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ulises
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    
    LibroServicio libroR;
    
    @Autowired
    
    AutorServicio autorR;
     
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo ){
        
        List<Autor>autores = autorR.listarAutor();
        
        modelo.addAttribute("autores", autores);
        
        return "FormularioLibro.html";
        
    }
    
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam Long idAutor, ModelMap modelo) throws MiException{
        
        try {
            libroR.crearLibro(nombre, idAutor);
            modelo.put("exito", "El libro fue registrado correctamente");
            return "panel.html";
        } catch (MiException e) {
            List<Autor>autores = autorR.listarAutor();
        
            modelo.addAttribute("autores", autores);
            modelo.put("error", e.getMessage()); 
            return "FormularioLibro.html";
        }
        
    }
    
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
    
        List<Libro> libros = libroR.listarLibro();
        
        modelo.addAttribute("libros", libros);
        
        return "libro_list.html";
    
    }
    
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo){
    
        modelo.put("libro", libroR.getOne(id));
        List<Autor>autores = autorR.listarAutor();
        
        modelo.addAttribute("autores", autores);
        
        return "libro_modificar.html";
    
    }
    
    //OJO le agrego un pathvariable al id autor
//    @PostMapping("/modificar/{id}")
//    public String modificar(@PathVariable Long id, String nombre, @PathVariable Long idAutor, ModelMap modelo){
//    
//        try {
//            libroR.modificarLirbro(id, nombre, idAutor);
//             return "redirect:../lista";
//        } catch (Exception e) {
//            System.out.println("ERROR MODIFICAR");
//            modelo.put("error", e.getMessage());
//            return "libro_modificar.html";
//        }
//        
//    }
    
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, String nombre, @RequestParam Long idAutor, ModelMap modelo){
    
        try {
            libroR.modificarLirbro(id, nombre, idAutor);
             return "redirect:../lista";
        } catch (Exception e) {
            System.out.println("ERROR MODIFICAR");
            modelo.put("error", e.getMessage());
            return "libro_modificar.html";
        }
        
    }
}
