package joao.alves.delivery.marketplace.dto.response;

import joao.alves.delivery.marketplace.domain.Prato;

import java.math.BigDecimal;

public record PratoResponse(
        Long id,
        Long categoriaId,
        String nome,
        BigDecimal preco,
        Prato.Disponivel disponivel
) {
}
