package techlab.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techlab.api.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Podemos agregar consultas personalizadas aquí si es necesario
}
