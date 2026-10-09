package joao.alves.delivery.marketplace.dto.response;

import joao.alves.delivery.marketplace.domain.Pagamento;

import java.math.BigDecimal;

public record PagamentoResponse(
        Long id,
        Long pedidoId,
        Pagamento.Forma formaPagamento,
        BigDecimal valor,
        Pagamento.StatusPagamento statusPagamento
) {
}
