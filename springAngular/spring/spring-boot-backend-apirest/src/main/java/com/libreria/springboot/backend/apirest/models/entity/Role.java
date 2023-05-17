package com.libreria.springboot.backend.apirest.models.entity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name="roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min=4, max=20)
    @Column(unique = true)
    private String nombre;

    private static final long serialVersionUID = 1L;
}
