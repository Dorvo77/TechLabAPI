package techlab.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lineas_pedido")
@Data
@NoArgsConstructor
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private double precioUnitario; // Guardamos el precio al momento de la compra

    @Column(nullable = false)
    private double subtotal;

    // Relación: Muchas líneas pertenecen a un producto
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Relación: Muchas líneas pertenecen a un pedido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonIgnore // Evita recursión infinita en la serialización JSON
    private Pedido pedido;

    public LineaPedido(Producto producto, int cantidad, Pedido pedido) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pedido = pedido;
        this.precioUnitario = producto.getPrecio();
        this.subtotal = producto.getPrecio() * cantidad;
    }
}