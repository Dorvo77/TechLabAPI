package techlab.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private double total;

    // Relación: Un pedido tiene muchas líneas de pedido
    // CascadeType.ALL: Si guardamos/eliminamos un Pedido, se aplica a sus Lineas.
    // orphanRemoval = true: Si quitamos una LineaPedido de la lista, se borra de la BD.
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LineaPedido> lineasPedido = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    public void calcularTotal() {
        this.total = lineasPedido.stream()
                .mapToDouble(LineaPedido::getSubtotal)
                .sum();
    }
}