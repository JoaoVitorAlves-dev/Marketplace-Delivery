package joao.alves.delivery.marketplace.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import joao.alves.delivery.marketplace.domain.Pagamento;

import java.math.BigDecimal;

public record PagamentoRequest(
        @NotNull Long pedidoId,
        @NotNull Pagamento.Forma formaPagamento,
        @NotNull @Positive BigDecimal valor,
        @NotNull Pagamento.StatusPagamento statusPagamento
) {
}
