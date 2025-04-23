package org.example.rdcmmatricula.entity;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import org.example.rdcmmatricula.dato.Estudiante;
import org.example.rdcmmatricula.dato.Curso;

import java.time.LocalDate;

@Entity
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer estudianteId;
    private Integer cursoId;
    private Integer ciclo;
    private LocalDate fecha;

    @Transient
    private Estudiante estudiante;

    @Transient
    private Curso curso;

    public Matricula() {}

    public Matricula(Integer id, Integer estudianteId, Integer cursoId, Integer ciclo, LocalDate fecha) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
        this.ciclo = ciclo;
        this.fecha = fecha;
    }

    // Getters y Setters omitidos por brevedad
}