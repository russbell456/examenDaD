package org.example.rdcmmatricula.service.impl;

import org.example.rdcmmatricula.dato.Cliente;
import org.example.rdcmmatricula.dato.Producto;
import org.example.rdcmmatricula.entity.Pedido;
import org.example.rdcmmatricula.entity.PedidoDetalle;
import org.example.rdcmmatricula.feing.ClienteFeing;
import org.example.rdcmmatricula.feing.ProductoFeing;
import org.example.rdcmmatricula.repository.PedidoRepository;
import org.example.rdcmmatricula.service.PedidoService;
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
