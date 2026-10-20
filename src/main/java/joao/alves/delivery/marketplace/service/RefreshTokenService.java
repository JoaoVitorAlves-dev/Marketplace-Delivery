package joao.alves.delivery.marketplace.service;

import jakarta.transaction.Transactional;
import joao.alves.delivery.marketplace.domain.RefreshToken;
import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${api.expiration.in.days:7}")
    private long expirationInDays;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken criar(Usuario usuario) {
        refreshTokenRepository.deleteByUsuario(usuario);

        RefreshToken rt = new RefreshToken();
        rt.setUsuario(usuario);
        rt.setExpiraEm(Instant.now().plus(expirationInDays, ChronoUnit.DAYS));
        rt.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(rt);
    }

    @Transactional
    public RefreshToken validarERotacionar(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token não encontrado."));

        if (refreshToken.getExpiraEm().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Token expirado.");
        }

        return criar(refreshToken.getUsuario());
    }

    @Transactional
    public void apagarPorToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }
}
