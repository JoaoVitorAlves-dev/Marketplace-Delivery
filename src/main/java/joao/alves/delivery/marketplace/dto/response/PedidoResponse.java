package joao.alves.delivery.marketplace.dto.response;

import joao.alves.delivery.marketplace.domain.Pedido;

import java.math.BigDecimal;

public record PedidoResponse(
        Long id,
        Long usuarioId,
        Long restauranteId,
        Long enderecoId,
        Pedido.Status status,
        BigDecimal total
) {
}
