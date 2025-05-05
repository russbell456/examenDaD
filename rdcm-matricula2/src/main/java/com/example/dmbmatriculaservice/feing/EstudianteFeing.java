package com.example.dmbmatriculaservice.feing;

import com.example.dmbmatriculaservice.dto.Estudiante;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "dmb-student-service", path = "/estudiantes")
public interface EstudianteFeing {
        @GetMapping("/{id}")
        ResponseEntity<Estudiante> obtenerPorId (@PathVariable Integer id);
}
