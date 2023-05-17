package com.libreria.springboot.backend.apirest.models.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    @Size(min=5, max=25)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max=60)
    private String password;

    private Boolean enabled;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    @Email
    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="usuarios_roles", joinColumns = @JoinColumn(name="usuario_id")
    , inverseJoinColumns = @JoinColumn(name="role_id"),
    uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "role_id"})})
    private List<Role> roles;

    private static final long serialVersionUID = 1L;
}
