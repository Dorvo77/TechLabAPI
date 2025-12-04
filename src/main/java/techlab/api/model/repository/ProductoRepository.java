package techlab.api.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import techlab.api.model.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Spring Data JPA crea esta consulta automáticamente:
    // "SELECT * FROM productos WHERE nombre LIKE %?% IGNORE CASE"
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    //SQL ("SELECT * FROM productos WHERE stock > ?")
    List<Producto> findByStockGreaterThan(Integer stock);

}