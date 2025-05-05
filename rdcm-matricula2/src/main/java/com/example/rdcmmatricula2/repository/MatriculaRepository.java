package com.example.rdcmmatricula2.repository;

import com.example.rdcmmatricula2.entity.Matricula;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    List<Matricula> getMatriculaById(Integer id, Sort sort);
}

