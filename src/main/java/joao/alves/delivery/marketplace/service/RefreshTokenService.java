package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.domain.RefreshToken;
import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Value("${api.expiration.in.days:7}")
    private long expirationInDays;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken criar(Usuario usuario) {
    }
}
