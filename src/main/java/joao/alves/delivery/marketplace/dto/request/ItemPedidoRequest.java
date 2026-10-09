package joao.alves.delivery.marketplace.dto.request;

import java.math.BigDecimal;

public record ItemPedidoRequest(
        Long pedidoId,
        Long pratoId,
        Long quantidade,
        BigDecimal precoUnitario
) {
}
