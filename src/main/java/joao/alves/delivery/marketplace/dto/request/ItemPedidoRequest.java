package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequest(
        @NotNull Long pedidoId,
        @NotNull Long pratoId,
        @NotNull @Positive Long quantidade
) {
}
