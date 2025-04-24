package com.example.rdcmestudiante.util;

import com.example.rdcmestudiante.entity.Estudiante;
import com.example.rdcmestudiante.repository.EstudianteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EstudianteSeeder implements CommandLineRunner {

    private final EstudianteRepository estudianteRepository;

    public EstudianteSeeder(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public void run(String... args) {
        // Verificamos si ya existen datos para no duplicar
        if (estudianteRepository.count() == 0) {
            // Estudiantes con estado "Activo"
            Estudiante estudiante1 = new Estudiante(null, "Juan Pérez", "Ingeniería", "Activo", 3, "12345678");
            Estudiante estudiante2 = new Estudiante(null, "María López", "Arquitectura", "Activo", 4, "87654321");
            Estudiante estudiante3 = new Estudiante(null, "Carlos Sánchez", "Medicina", "Activo", 2, "11223344");

            // Estudiantes con estado "Inactivo"
            Estudiante estudiante4 = new Estudiante(null, "Ana García", "Derecho", "Inactivo", 5, "22334455");
            Estudiante estudiante5 = new Estudiante(null, "Pedro Martínez", "Contabilidad", "Inactivo", 1, "99887766");
            Estudiante estudiante6 = new Estudiante(null, "Laura Fernández", "Psicología", "Inactivo", 3, "66778899");

            // Guardamos los estudiantes en la base de datos
            estudianteRepository.save(estudiante1);
            estudianteRepository.save(estudiante2);
            estudianteRepository.save(estudiante3);
            estudianteRepository.save(estudiante4);
            estudianteRepository.save(estudiante5);
            estudianteRepository.save(estudiante6);

            System.out.println("Datos de estudiantes insertados correctamente.");
        } else {
            System.out.println("Los estudiantes ya existen, no se insertaron datos.");
        }
    }
}
