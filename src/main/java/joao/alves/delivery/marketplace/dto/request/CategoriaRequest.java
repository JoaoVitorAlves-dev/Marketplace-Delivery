package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoriaRequest(
        @NotNull Long restauranteId,
        @NotBlank String nome
) {
}
