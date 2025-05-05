package com.example.dmbmatriculaservice.controller;

import com.example.dmbmatriculaservice.entity.Matricula;
import com.example.dmbmatriculaservice.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
    @Autowired
    private MatriculaService matriculaService;

    @GetMapping
    public ResponseEntity<List<Matricula>> listar() {
        return ResponseEntity.ok(matriculaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> buscar(@PathVariable Integer id) {
        return matriculaService.buscar(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Matricula> guardar (@RequestBody Matricula matricula) {
        return ResponseEntity.ok(matriculaService.guardar(matricula));
    }

    @PutMapping("/{id}")
    public Matricula actualizar(@PathVariable Integer id, @RequestBody Matricula matricula) {
        return matriculaService.actualizar(id, matricula);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        matriculaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
