/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Ulises
 */
@Entity
@Table(name="clientes")
public class NewClass implements Serializable{
    
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Date creatAt;

}
