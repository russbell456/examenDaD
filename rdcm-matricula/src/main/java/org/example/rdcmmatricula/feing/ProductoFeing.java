package org.example.rdcmmatricula.feing;


import org.example.rdcmmatricula.dato.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ms-catalogo-service",path = "/productos")
public interface ProductoFeing {
    @GetMapping("/{id}")
    public ResponseEntity<Producto> listById(@PathVariable (required=true) Integer id);
}
