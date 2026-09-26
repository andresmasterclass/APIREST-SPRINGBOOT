package com.inventario.api_productos.controller;

import com.inventario.api_productos.exception.StockInsuficienteException;
import com.inventario.api_productos.model.Producto;
import com.inventario.api_productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET: Obtener todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.obtenerTodos();
    }

    // GET: Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(producto -> ResponseEntity.ok(producto))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Filtrar por categoria
    @GetMapping("/categoria/{categoria}")
    public List<Producto> listarPorCategoria(@PathVariable String categoria) {
        return productoService.obtenerPorCategoria(categoria);
    }

    // POST: Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardar(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // PUT: Actualizar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto actualizado = productoService.actualizar(id, producto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET: Reto 1 - Filtrar productos con precio menor a un valor (query param)
    @GetMapping("/precio-menor")
    public List<Producto> listarPorPrecioMenorA(@RequestParam Double precio) {
        return productoService.obtenerPorPrecioMenorA(precio);
    }

    // PATCH: Reto 2 - Reducir stock de un producto
    @PatchMapping("/{id}/reducir-stock")
    public ResponseEntity<?> reducirStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        try {
            Producto actualizado = productoService.reducirStock(id, cantidad);
            return ResponseEntity.ok(actualizado);
        } catch (StockInsuficienteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
