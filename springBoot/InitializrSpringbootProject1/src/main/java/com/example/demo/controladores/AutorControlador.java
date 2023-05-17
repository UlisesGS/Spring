/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Autor;
import com.example.demo.excepciones.MiException;
import com.example.demo.servicio.AutorServicio;
import java.util.List;
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
@RequestMapping("/autor")

public class AutorControlador {
    
    @Autowired
    
    AutorServicio autorR;
    
    @GetMapping("/registrar")
    public String registrar(){
    
        return "FormularioAutor.html";
    
    }
    
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        
        try {
            autorR.crearAutor(nombre);
            modelo.put("exito", "El autor fue registrado correctamente");
            return "indexl.html";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "FormularioAutor.html";
        }
        
    }
    
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
    
        List <Autor> autores = autorR.listarAutor();
        
        modelo.addAttribute("autores",autores);
        
        return "autor_list.html";
        
    }
    
    //SE TIENE QUE USAR STRING Y LO CAMBIO A LONG
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo){
        
        modelo.put("autor", autorR.getOne(id));
        
        return "autor_modificar.html";
    }
    
    //ME TIRA ERROR 500 POR NO PODER VALIDAR EL ID QUE ES LONG
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, String nombre, ModelMap modelo) {
    
        try {
            autorR.modificarAutor(id, nombre);
            modelo.put("exito", "El autor fue registrado correctamente");
            return "redirect:../lista";
        } catch (Exception e) {
            System.out.println("ERROR MODIFICAR");
            modelo.put("error", e.getMessage());
            return "autor_modificar.html";
        }
        
        
    }
}
