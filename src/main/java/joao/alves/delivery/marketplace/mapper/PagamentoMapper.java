package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.Pagamento;
import joao.alves.delivery.marketplace.domain.Pedido;
import joao.alves.delivery.marketplace.dto.request.PagamentoRequest;
import joao.alves.delivery.marketplace.dto.response.PagamentoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PagamentoMapper {

    public Pagamento toEntity(PagamentoRequest pagamentoRequest) {
        Pagamento pagamento = new Pagamento();

        Pedido pedido = new Pedido();
        pedido.setId(pagamentoRequest.pedidoId());

        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(pagamentoRequest.formaPagamento());
        pagamento.setValor(pagamentoRequest.valor());
        pagamento.setStatusPagamento(pagamentoRequest.statusPagamento());

        return pagamento;
    }

    public PagamentoResponse toDTO(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getId(), pagamento.getPedido().getId(), pagamento.getFormaPagamento(),
                pagamento.getValor(), pagamento.getStatusPagamento()
        );
    }

}
