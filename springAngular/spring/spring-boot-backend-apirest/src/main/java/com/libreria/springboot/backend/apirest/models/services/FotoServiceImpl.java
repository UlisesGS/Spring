package com.libreria.springboot.backend.apirest.models.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FotoServiceImpl implements FotoService{
   private final Logger log = LoggerFactory.getLogger(FotoServiceImpl.class);
    @Override
    public Resource cargar(String nombreFoto) throws MalformedURLException {
    Path rutaArchivo = getpath(nombreFoto);
        Resource recurso = new UrlResource(rutaArchivo.toUri());
        log.info(rutaArchivo.toString());

        if (!recurso.exists() && !recurso.isReadable()){
            rutaArchivo = Paths.get("src/main/resources/static/img").resolve("no-user.png").toAbsolutePath();


            log.error("Error no se puede cargar la imagen"+ nombreFoto);
        }
        return recurso;
    }

    @Override
    public String copiar(MultipartFile archivo) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString()+ " "+ archivo.getOriginalFilename().replace(" ","" );
        Path rutaArchivo = getpath(nombreArchivo);
        log.info(rutaArchivo.toString());
      Files.copy(archivo.getInputStream(),rutaArchivo);


        return nombreArchivo;
    }

    @Override
    public boolean eliminar(String nombreFoto) {
        if(nombreFoto!=null && nombreFoto.length()>0){
            Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
            File archivoFotoAnterior = rutaFotoAnterior.toFile();
            if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                archivoFotoAnterior.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getpath(String nombreFoto) {
       Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
       return rutaArchivo;
    }
}
