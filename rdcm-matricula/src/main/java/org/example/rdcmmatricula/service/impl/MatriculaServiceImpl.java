package org.example.rdcmmatricula.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.rdcmmatricula.dato.Curso;
import org.example.rdcmmatricula.dato.Estudiante;
import org.example.rdcmmatricula.entity.Matricula;
import org.example.rdcmmatricula.entity.MatriculaDetalle;
import org.example.rdcmmatricula.feign.CursoFeign;
import org.example.rdcmmatricula.feign.EstudianteFeign;
import org.example.rdcmmatricula.repository.MatriculaRepository;
import org.example.rdcmmatricula.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaServiceImpl implements MatriculaService {

    @Autowired
    private MatriculaRepository repository;

    @Autowired
    private EstudianteFeign estudianteFeign;

    @Autowired
    private CursoFeign cursoFeign;

    @Override
    public List<Matricula> listar() {
        List<Matricula> matriculas = repository.findAll();

        for (Matricula matricula : matriculas) {
            Estudiante estudiante = estudianteFeign.obtenerPorId(matricula.getEstudianteId()).getBody();
            matricula.setEstudiante(estudiante);

            for (MatriculaDetalle detalle : matricula.getDetalles()) {
                Curso curso = cursoFeign.obtenerPorId(detalle.getCursoId()).getBody();
                detalle.setCurso(curso);
            }
        }

        return matriculas;
    }

    @Override
    @CircuitBreaker(name = "matriculaRegistrarCB", fallbackMethod = "FallBackMethodGuardar")
    public Matricula registrar(Matricula matricula) {
        Estudiante estudiante = estudianteFeign.obtenerPorId(matricula.getEstudianteId()).getBody();
        if (estudiante == null || !"Activo".equals(estudiante.getEstado())) {
            throw new RuntimeException("Estudiante no válido o inactivo");
        }

        List<Matricula> matriculasPrevias = repository.findByEstudianteId(estudiante.getId());
        for (MatriculaDetalle detalleNuevo : matricula.getDetalles()) {
            for (Matricula m : matriculasPrevias) {
                for (MatriculaDetalle detalleExistente : m.getDetalles()) {
                    if (detalleExistente.getCursoId().equals(detalleNuevo.getCursoId())) {
                        throw new RuntimeException("El estudiante ya está matriculado en el curso ID: " + detalleNuevo.getCursoId());
                    }
                }
            }
        }

        matricula.setCiclo(estudiante.getCicloActual());
        matricula.setFecha(LocalDate.now());

        for (MatriculaDetalle detalle : matricula.getDetalles()) {
            Curso curso = cursoFeign.obtenerPorId(detalle.getCursoId()).getBody();
            if (curso == null || curso.getCapacidad() <= 0) {
                throw new RuntimeException("Curso no disponible o sin capacidad");
            }

            curso.setCapacidad(curso.getCapacidad() - 1);
            cursoFeign.actualizarCurso(curso.getId(), curso);
            detalle.setCurso(curso);
        }

        return repository.save(matricula);
    }

    public Matricula FallBackMethodGuardar(Matricula matricula, Throwable t) {
        System.err.println("🚨 Fallback registrar activado: " + t.getMessage());
        matricula.setId(-1);
        Estudiante errorEstudiante = new Estudiante();
        errorEstudiante.setNombre("⚠️ Servicio de estudiante no disponible");
        errorEstudiante.setEstado("Desconocido");
        matricula.setEstudiante(errorEstudiante);
        matricula.setDetalles(new ArrayList<>());
        return matricula;
    }

    @Override
    @CircuitBreaker(name = "matriculaObtenerCB", fallbackMethod = "FallBackMethodObtener")
    public Optional<Matricula> obtener(Integer id) {
        Optional<Matricula> optionalMatricula = repository.findById(id);

        optionalMatricula.ifPresent(matricula -> {
            Estudiante estudiante = estudianteFeign.obtenerPorId(matricula.getEstudianteId()).getBody();
            matricula.setEstudiante(estudiante);

            for (MatriculaDetalle detalle : matricula.getDetalles()) {
                Curso curso = cursoFeign.obtenerPorId(detalle.getCursoId()).getBody();
                detalle.setCurso(curso);
            }
        });

        return optionalMatricula;
    }

    public Optional<Matricula> FallBackMethodObtener(Integer id, Throwable t) {
        System.err.println("🚨 Fallback obtener activado para id " + id + ": " + t.getMessage());
        Matricula fallback = new Matricula();
        fallback.setId(-1);
        Estudiante fallbackEstudiante = new Estudiante();
        fallbackEstudiante.setNombre("⚠️ Servicio no disponible");
        fallbackEstudiante.setEstado("Desconocido");
        fallback.setEstudiante(fallbackEstudiante);
        fallback.setDetalles(new ArrayList<>());
        return Optional.of(fallback);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
