package techlab.api.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techlab.api.model.LineaPedido;
import techlab.api.model.Pedido;
import techlab.api.model.Producto;
import techlab.api.model.dto.LineaPedidoRequestDTO;
import techlab.api.model.dto.PedidoRequestDTO;
import techlab.api.model.exception.ResourceNotFoundException;
import techlab.api.model.exception.StockInsuficienteException;
import techlab.api.model.repository.PedidoRepository;
import techlab.api.model.repository.ProductoRepository;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));
    }

    @Transactional // ¡MUY IMPORTANTE! Si algo falla, revierte todos los cambios en la BD
    public Pedido createPedido(PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = new Pedido();

        for (LineaPedidoRequestDTO itemDTO : pedidoRequestDTO.getItems()) {
            // 1. Buscar el producto
            Producto producto = productoRepository.findById(itemDTO.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + itemDTO.getProductoId()));

            // 2. Validar stock
            if (producto.getStock() < itemDTO.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para: " + producto.getNombre() +
                        ". Disponible: " + producto.getStock() +
                        ", Solicitado: " + itemDTO.getCantidad());
            }

            // 3. Crear la línea de pedido
            LineaPedido linea = new LineaPedido(producto, itemDTO.getCantidad(), pedido);
            pedido.getLineasPedido().add(linea);

            // 4. Actualizar el stock del producto
            producto.setStock(producto.getStock() - itemDTO.getCantidad());
            productoRepository.save(producto);
        }

        // 5. Calcular total y guardar el pedido
        pedido.calcularTotal();
        return pedidoRepository.save(pedido);
    }
}