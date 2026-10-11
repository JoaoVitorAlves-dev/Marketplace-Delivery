package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import joao.alves.delivery.marketplace.domain.Prato;

import java.math.BigDecimal;

public record PratoRequest(
        @NotNull Long categoriaId,
        @NotBlank String nome,
        @NotNull @Positive BigDecimal preco,
        @NotNull Prato.Disponivel disponivel
) {
}
