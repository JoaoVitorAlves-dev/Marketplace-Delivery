package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotNull;
import joao.alves.delivery.marketplace.domain.Pedido;

public record PedidoStatusRequest(@NotNull Pedido.Status status, Long entregadorId) {
}


