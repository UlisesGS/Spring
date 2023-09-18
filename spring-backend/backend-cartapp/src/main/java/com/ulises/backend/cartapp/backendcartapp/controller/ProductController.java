package com.ulises.backend.cartapp.backendcartapp.controller;

import com.ulises.backend.cartapp.backendcartapp.entity.Producto;
import com.ulises.backend.cartapp.backendcartapp.service.FotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ulises.backend.cartapp.backendcartapp.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cartapp")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @Autowired
    private FotoService fotoService;
 
    @GetMapping("/productos")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            return this.validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(producto));
    }

    @PostMapping("clientes/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo , @RequestParam("id") Long id){
        Map<String,Object> respuesta = new HashMap<>();

        Optional<Producto> productoOptional = productService.findById(id);

        Producto producto = productoOptional.get();

        if (!archivo.isEmpty()){
            String nombreArchivo = null;
            try {
                nombreArchivo=fotoService.copiar(archivo);
            } catch (IOException e) {
                respuesta.put("error",e.getMessage()+ " ");
                respuesta.put("mensaje", "error al cargar la foto");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }
            String nombreFotoAnterior = producto.getFoto();
            fotoService.eliminar(nombreFotoAnterior);

            producto.setFoto(nombreArchivo);
            productService.save(producto);
            respuesta.put("cliente", producto);
            respuesta.put("mensaje", "Ha subido correctamente la imagen"+ nombreArchivo );

        }



        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(e -> {
            errores.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }
}
