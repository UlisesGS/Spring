/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.MiException;
import com.example.demo.servicio.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ulises
 */


@Controller
@RequestMapping("/")
public class IndexControlador {
    
    @Autowired
    private UsuarioServicio usuarioSer;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("")
    public String index(HttpSession session){
        
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
        if (logueado!=null) {
            
            if (logueado.getRol().toString().equals("ADMIN")) {
                
            return "redirect:/admin/dashboard";
            
            } else {
                
            return "redirect:/user/usuario";
            
            }
            
        }else{
            
            return "inicio.html";
        
        }
        
        
        
        
    }
    
    
    @GetMapping("/registrar")
    public String registrar(){
        return "registro.html";
    }
    
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,  String password2, ModelMap modelo){
        
        try {
            usuarioSer.registrar(nombre, email, password, password2);
            
            modelo.put("exito", "Usario registrado correctamente");
            
            return "index.html";
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            
            return "registro.html";
        }
        
    }
    
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo){
        
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }
        
        return "login.html";
    }
    
    
    @GetMapping("/inicio")
    public String inicio(){
        return "inicio.html";
    }
    
    
    @GetMapping("/panel")
    public String panel(){
        return "panel.html";
    }
    
    
//    @GetMapping("/user_autor")
//    public String panel(){
//        return "panel.html";
//    }
//    
//    
//    @GetMapping("/panel")
//    public String panel(){
//        return "panel.html";
//    }
}
