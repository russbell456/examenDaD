package com.example.dmbmatriculaservice.repository;

import com.example.dmbmatriculaservice.entity.Matricula;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    List<Matricula> getMatriculaById(Integer id, Sort sort);
}

