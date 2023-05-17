package com.libreria.springboot.backend.apirest.models.entity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    @Size(min=4, max=16)
    private String nombre;

    @NotBlank
    @NotEmpty
    @Size(min=4, max=16)
    private String apellido;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @Column(name="create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    private String foto;

    @ManyToOne
    @JoinColumn(name="region_id") /*SE PUEDE OMITIR*/
    @NotNull
    private Region region;

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }

}
