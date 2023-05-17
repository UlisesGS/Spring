package com.libreria.springboot.backend.apirest.controllers;

import com.libreria.springboot.backend.apirest.models.entity.Cliente;
import com.libreria.springboot.backend.apirest.models.entity.Region;
import com.libreria.springboot.backend.apirest.models.services.FotoService;
import com.libreria.springboot.backend.apirest.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {


    @Autowired
    private IClienteService clienteService;

    /*private final Logger log = (Logger) LoggerFactory.getLogger(ClienteRestController.class);*/

    @Autowired
    private FotoService fotoService;

    @GetMapping("/clientes")
    public List<Cliente> index() {

        return clienteService.findAll();
    }


    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page) {

        return clienteService.findAll(PageRequest.of(page, 4));
    }


    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/clientes/{id}")
    /*CUALQUIER TIPO DE OBJETO*/
    public ResponseEntity<?> show(@PathVariable Long id) {

        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteService.findById(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");        /*ESPECIFICA MAS EL ERROR*/
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }


    @Secured("ROLE_ADMIN")
    @PostMapping("/clientes")
    /*AGREGA LAS VALIDACIONES DE LA ENTIDAD*/        /*ESTE OBJETO SE USA POR SI TIRA ERROR EL VALID*/
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) { /*NOS ENVIAN EL CLIENTE EN FORMATO JSON Y LO PASAMOS A CLIENTE*/

        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    /*TRANSFORMAMOS EL TIPO DE DATO ERROR EN STRING*/
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            /*EL SAVE PODRIA TIRAR UN ERROR SI NO QUEREMOS QUE SE REPITA EL EMAIL*/
            clienteNew = clienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");        /*ESPECIFICA MAS EL ERROR*/
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido creado con exito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    @Secured("ROLE_ADMIN")
    @PutMapping("/clientes/{id}")/*PARA EDITAR SE USA EL PUT PARA ACTUALIZAR*/
    /*BindingResult VA DESPUES DE Valid*/
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {

        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdated = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    /*TRANSFORMAMOS EL TIPO DE DATO ERROR EN STRING*/
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (clienteActual == null) {
            response.put("mensaje", "Error: no se pudo editar, El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setCreateAt(cliente.getCreateAt());
            clienteActual.setRegion(cliente.getRegion());

            clienteUpdated = clienteService.save(clienteActual);

        } catch (DataAccessException e) {
            /*NO ME TIRA EL MENSAJE*/
            response.put("mensaje", "Error al actualizar el cliente en la base de datos");        /*ESPECIFICA MAS EL ERROR*/
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido actualizado con exito");
        response.put("cliente", clienteUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }


    @Secured("ROLE_ADMIN")
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();


        try {

            Cliente cliente = clienteService.findById(id);


            if (cliente == null) {
                response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
            String nombreFoto = cliente.getFoto();
            fotoService.eliminar(nombreFoto);
            clienteService.delete(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente en la base de datos");        /*ESPECIFICA MAS EL ERROR*/
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido eliminado con exito");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    /*@Secured({"ROLE_ADMIN","ROLE_USER"})*/
    @PostMapping("clientes/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo")MultipartFile archivo , @RequestParam("id") Long id){
        Map<String,Object> respuesta = new HashMap<>();

        Cliente cliente = clienteService.findById(id);

            if (!archivo.isEmpty()){
                String nombreArchivo = null;
                try {
                    nombreArchivo=fotoService.copiar(archivo);
                } catch (IOException e) {
                    respuesta.put("error",e.getMessage()+ " ");
                    respuesta.put("mensaje", "error al cargar la foto");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
                }
                String nombreFotoAnterior = cliente.getFoto();
                fotoService.eliminar(nombreFotoAnterior);

                cliente.setFoto(nombreArchivo);
                clienteService.save(cliente);
                respuesta.put("cliente", cliente);
                respuesta.put("mensaje", "Ha subido correctamente la imagen"+ nombreArchivo );

            }



        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }


    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){

        Resource recurso = null;

        try {
            recurso = fotoService.cargar(nombreFoto);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders cabecera = new HttpHeaders();

        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() +  "\"");

        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/clientes/regiones")
    public List<Region> listarRegiones(){
        return clienteService.findAllRegiones();
    }
}

