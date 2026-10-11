package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoUpdateRequest(
        @NotNull @Positive Long quantidade
) {
}
