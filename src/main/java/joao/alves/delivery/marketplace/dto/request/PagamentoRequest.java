package joao.alves.delivery.marketplace.dto.request;

import joao.alves.delivery.marketplace.domain.Pagamento;

import java.math.BigDecimal;

public record PagamentoRequest(
        Long pedidoId,
        Pagamento.Forma formaPagamento,
        BigDecimal valor,
        Pagamento.StatusPagamento statusPagamento
) {
}
