package org.example.rdcmmatricula.service;

import org.example.rdcmmatricula.entity.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {


    List<Pedido> listar();
    Optional<Pedido> buscar (Integer id);
    Pedido guardar (Pedido pedido);
    Pedido actualizar(Integer id ,Pedido pedido);
    void Eliminar(Integer id);
}
