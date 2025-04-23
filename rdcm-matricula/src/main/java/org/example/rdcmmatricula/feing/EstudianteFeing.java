package org.example.rdcmmatricula.feing;

import org.example.rdcmmatricula.dato.Estudiante;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rdcm-estudiante-service", path = "/estudiantes")
public interface EstudianteFeing {
    @GetMapping("/{id}")
    ResponseEntity<Estudiante> obtenerPorId(@PathVariable Integer id);
}
