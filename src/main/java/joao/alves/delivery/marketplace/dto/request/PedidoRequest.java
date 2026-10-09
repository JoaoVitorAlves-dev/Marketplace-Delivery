package joao.alves.delivery.marketplace.dto.request;

import joao.alves.delivery.marketplace.domain.Pedido;

import java.math.BigDecimal;

public record PedidoRequest(
        Long usuarioId,
        Long restauranteId,
        Long enderecoId,
        Pedido.Status statusPedido,
        BigDecimal total
) {
}
