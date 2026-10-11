package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
        @NotBlank String nome
) {
}
