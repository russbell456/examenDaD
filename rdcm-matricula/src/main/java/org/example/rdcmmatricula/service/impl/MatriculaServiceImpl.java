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
import java.util.Collections;
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
    @CircuitBreaker(name = "matriculaListarCB", fallbackMethod = "fallbackListar")
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
    @CircuitBreaker(name = "matriculaObtenerCB", fallbackMethod = "fallbackObtener")
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

    @Override
    @CircuitBreaker(name = "matriculaRegistrarCB", fallbackMethod = "fallbackRegistrar")
    public Matricula registrar(Matricula matricula) {
        Estudiante estudiante = estudianteFeign.obtenerPorId(matricula.getEstudianteId()).getBody();
        if (estudiante == null || !"Activo".equals(estudiante.getEstado())) {
            throw new RuntimeException("Estudiante no válido o inactivo");
        }

        // Verifica si el estudiante ya se matriculó al curso
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

            // Reduce la capacidad en 1
            curso.setCapacidad(curso.getCapacidad() - 1);
            cursoFeign.actualizarCurso(curso.getId(), curso);

            detalle.setCurso(curso);
        }

        return repository.save(matricula);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    // --- MÉTODOS FALLBACK ---

    public List<Matricula> fallbackListar(Throwable t) {
        System.out.println("Fallback listar activado: " + t.getMessage());
        return Collections.emptyList();
    }

    public Optional<Matricula> fallbackObtener(Integer id, Throwable t) {
        System.out.println("Fallback obtener activado: " + t.getMessage());
        return Optional.empty();
    }

    public Matricula fallbackRegistrar(Matricula matricula, Throwable t) {
        System.out.println("Fallback registrar activado: " + t.getMessage());
        throw new RuntimeException("El servicio de registro de matrícula no está disponible temporalmente");
    }
}
