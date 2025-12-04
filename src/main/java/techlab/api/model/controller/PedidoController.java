package techlab.api.model.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techlab.api.model.Pedido;
import techlab.api.model.dto.PedidoRequestDTO;
import techlab.api.model.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        Pedido pedido = pedidoService.getPedidoById(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> createPedido(@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        Pedido nuevoPedido = pedidoService.createPedido(pedidoRequestDTO);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }
}