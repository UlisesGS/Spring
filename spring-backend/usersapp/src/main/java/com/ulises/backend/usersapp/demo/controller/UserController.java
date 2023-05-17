package com.ulises.backend.usersapp.demo.controller;

import com.ulises.backend.usersapp.demo.entity.User;
import com.ulises.backend.usersapp.demo.service.UserService;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> findAll(){
        List<User> users = userService.findAll();

        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(userService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }

        return  ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody User user, BindingResult result){

        if (result.hasErrors()){
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){
        Optional<User> userOptional=userService.findById(id);

        if (result.hasErrors()){
            return validation(result);
        }

        if (userOptional.isPresent()){
            User userDb = userOptional.get();
            /*VER EL TEMA DE LA CONTRASEÑA*/
            userDb.setUsername(user.getUsername());
            userDb.setPassword(user.getPassword());
            userDb.setEmail(user.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDb));
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){

        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent()){
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors= new HashMap<>();

        /*EL FIELDERRORRS DEVUELVE UNA LISTA, ENTONCES POR CADA UNO UTILIZAMOS ERRORS.PUT PARA AGARRAR Y DEVOLVER LOS ERRORES
        * UNO POR UNO  */

        result.getFieldErrors().forEach( err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
