package com.example.dmbmatriculaservice.feing;

import com.example.dmbmatriculaservice.dto.CursoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "dmb-curso-service", path = "/cursos")
public interface CursoFeing {
        @GetMapping("/{id}")
        ResponseEntity<CursoDto> obtenerPorId(@PathVariable Integer id);

        @PutMapping("/{id}")
        ResponseEntity<CursoDto> actualizarCurso(@PathVariable Integer id, @RequestBody CursoDto cursoDto);

}
