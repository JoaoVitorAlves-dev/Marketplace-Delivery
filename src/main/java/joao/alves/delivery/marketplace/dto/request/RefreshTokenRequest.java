package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank String accessToken
) {
}
