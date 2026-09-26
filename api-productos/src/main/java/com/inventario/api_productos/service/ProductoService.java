package com.inventario.api_productos.service;

import com.inventario.api_productos.exception.StockInsuficienteException;
import com.inventario.api_productos.model.Producto;
import com.inventario.api_productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto productoDetalles) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));

        producto.setNombre(productoDetalles.getNombre());
        producto.setPrecio(productoDetalles.getPrecio());
        producto.setStock(productoDetalles.getStock());
        producto.setCategoria(productoDetalles.getCategoria());

        return productoRepository.save(producto);
    }

    public boolean eliminar(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Reto 1: productos con precio menor al valor indicado
    public List<Producto> obtenerPorPrecioMenorA(Double precio) {
        return productoRepository.findByPrecioLessThan(precio);
    }

    // Reto 2: reducir stock, lanzando error si es insuficiente
    public Producto reducirStock(Long id, Integer cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));

        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(
                    "Stock insuficiente. Stock actual: " + producto.getStock() + ", cantidad solicitada: " + cantidad);
        }

        producto.setStock(producto.getStock() - cantidad);
        return productoRepository.save(producto);
    }
}
