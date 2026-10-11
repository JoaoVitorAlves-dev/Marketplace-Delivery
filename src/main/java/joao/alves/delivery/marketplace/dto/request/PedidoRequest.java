package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotNull;

public record PedidoRequest(
        @NotNull Long usuarioId,
        @NotNull Long restauranteId,
        @NotNull Long enderecoId
) {
}
