package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequest(
        @NotNull Long usuarioId,
        @NotBlank String rua,
        @NotBlank String cidade,
        @NotBlank @Size(min = 2, max = 2) String estado,
        @NotBlank @Pattern(regexp = "\\d{8}") String cep
) {
}
