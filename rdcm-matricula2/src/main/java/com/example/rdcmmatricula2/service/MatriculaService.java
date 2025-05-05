package com.example.rdcmmatricula2.service;

import com.example.rdcmmatricula2.entity.Matricula;

import java.util.List;
import java.util.Optional;

public interface MatriculaService {
    List<Matricula> listar();
    Optional<Matricula> buscar(Integer id);
    Matricula guardar (Matricula matricula);
    Matricula actualizar (Integer id,Matricula matricula);
    void eliminar (Integer id);
}
