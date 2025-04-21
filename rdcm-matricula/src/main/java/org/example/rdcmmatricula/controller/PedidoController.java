package org.example.rdcmmatricula.controller;

import org.example.rdcmmatricula.entity.Pedido;
import org.example.rdcmmatricula.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService ;

    @GetMapping()
    public List<Pedido> listar(){
        return pedidoService.listar();
    }
    @GetMapping("/{id}")
    public Pedido buscar (@PathVariable Integer id) {
        return pedidoService.listar().get(id);
    }
    @PostMapping()
    public Pedido guardar( @RequestBody Pedido pedido){
        return pedidoService.guardar(pedido);
    }
    @PutMapping("/{id}")
    public Pedido actualizar(@PathVariable Integer id,@RequestBody Pedido pedido){
        pedido.setId(id);
        return pedidoService.actualizar(id, pedido);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        pedidoService.Eliminar(id);
    }

}
