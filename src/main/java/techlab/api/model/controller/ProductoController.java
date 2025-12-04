package techlab.api.model.controller;

import jakarta.validation.Valid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techlab.api.model.Producto;
import techlab.api.model.dto.ProductoDTO;
import techlab.api.model.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos") // Ruta base para todos los endpoints de productos
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.getProductoById(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/search")
    public List<Producto> searchProductos(@RequestParam String nombre) {
        return productoService.searchProductoByName(nombre);
    }

    @PostMapping
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        Producto nuevoProducto = productoService.createProducto(productoDTO);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO) {
        Producto productoActualizado = productoService.updateProducto(id, productoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
    // URL será: GET /api/productos/filter?minStock=20
    @GetMapping("/filter")
    public List<Producto> filterProductosByStock(@RequestParam int minStock) {
        return productoService.getProductosConStockMayorA(minStock);
    }
}