package joao.alves.delivery.marketplace.dto.response;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long id,
        Long pedidoId,
        Long pratoId,
        Long quantidade,
        BigDecimal precoUnitario
) {
}
