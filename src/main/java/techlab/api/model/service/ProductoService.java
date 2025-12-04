package techlab.api.model.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import techlab.api.model.Producto;
import techlab.api.model.dto.ProductoDTO;
import techlab.api.model.exception.ResourceNotFoundException;
import techlab.api.model.repository.ProductoRepository;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    public List<Producto> searchProductoByName(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Producto createProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Long id, ProductoDTO productoDTO) {
        Producto producto = getProductoById(id); // Reusa la validación

        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());

        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        Producto producto = getProductoById(id); // Valida que exista
        productoRepository.delete(producto);
    }
    //Buscar productos con stock mayor a ???
    public List<Producto> getProductosConStockMayorA(int cantidad) {
        return productoRepository.findByStockGreaterThan(cantidad);
    }
}