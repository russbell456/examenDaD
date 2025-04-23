package org.example.rdcmmatricula.feing;

import org.example.rdcmmatricula.dato.Curso;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rdcm-curso-service", path = "/cursos")
public interface CursoFeing {
    @GetMapping("/{id}")
    ResponseEntity<Curso> obtenerPorId(@PathVariable Integer id);
}
