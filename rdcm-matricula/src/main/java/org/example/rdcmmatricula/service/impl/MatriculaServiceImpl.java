package org.example.rdcmmatricula.service.impl;

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
    public Matricula registrar(Matricula matricula) {
        Estudiante estudiante = estudianteFeign.obtenerPorId(matricula.getEstudianteId()).getBody();
        if (estudiante == null || !"Activo".equals(estudiante.getEstado())) {
            throw new RuntimeException("Estudiante no válido o inactivo");
        }

        matricula.setCiclo(estudiante.getCiclo());
        matricula.setFecha(LocalDate.now());

        for (MatriculaDetalle detalle : matricula.getDetalles()) {
            Curso curso = cursoFeign.obtenerPorId(detalle.getCursoId()).getBody();
            if (curso == null || curso.getCapacidad() <= 0) {
                throw new RuntimeException("Curso no disponible o sin capacidad");
            }
            detalle.setCurso(curso);
        }

        return repository.save(matricula);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
