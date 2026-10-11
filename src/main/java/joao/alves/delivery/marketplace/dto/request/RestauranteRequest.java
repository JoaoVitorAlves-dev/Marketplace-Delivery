package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestauranteRequest(
        @NotNull Long usuarioId,
        @NotBlank String nome
) {
}
