package joao.alves.delivery.marketplace.dto.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {
}
