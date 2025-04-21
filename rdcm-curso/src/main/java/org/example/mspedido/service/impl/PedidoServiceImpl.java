package org.example.mspedido.service.impl;

import org.example.mspedido.dato.Cliente;
import org.example.mspedido.dato.Producto;
import org.example.mspedido.entity.Pedido;
import org.example.mspedido.entity.PedidoDetalle;
import org.example.mspedido.feing.ClienteFeing;
import org.example.mspedido.feing.ProductoFeing;
import org.example.mspedido.repository.PedidoRepository;
import org.example.mspedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;



    @Autowired
    private ProductoFeing productoFeing;

    @Autowired
    private ClienteFeing clienteFeing;

    @Override
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @Override
    public Optional<Pedido> buscar(Integer id) {
        Pedido pedido = pedidoRepository.findById(id).get();
        Cliente cliente = clienteFeing.listById(pedido.getClienteId()).getBody();
        List<PedidoDetalle> pedidoDetalles = pedido.getDetalle().stream().map(pedidoDetalle -> {
            Producto producto = productoFeing.listById(pedidoDetalle.getProductoId()).getBody();
            pedidoDetalle.setProducto(producto);
            return pedidoDetalle;
        }).collect(Collectors.toList());

        pedido.setDetalle(pedidoDetalles);
        pedido.setCliente(cliente);
        return Optional.of(pedido);
    }


    @Override
    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido actualizar(Integer id, Pedido pedido) {
            pedido.setId(id);
        return pedidoRepository.save(pedido);
    }

    @Override
    public void Eliminar(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
