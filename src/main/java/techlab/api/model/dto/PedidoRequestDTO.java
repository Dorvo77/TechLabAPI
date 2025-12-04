package techlab.api.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {

    @NotEmpty(message = "El pedido debe tener al menos un item")
    @Valid // Valida la lista de items
    private List<LineaPedidoRequestDTO> items;
}