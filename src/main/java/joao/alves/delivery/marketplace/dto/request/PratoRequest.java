package joao.alves.delivery.marketplace.dto.request;

import joao.alves.delivery.marketplace.domain.Prato;

import java.math.BigDecimal;

public record PratoRequest(
        Long categoriaId,
        String nome,
        BigDecimal preco,
        Prato.Disponivel disponivel
) {
}
