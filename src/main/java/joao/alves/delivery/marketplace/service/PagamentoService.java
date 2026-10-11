package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.domain.Pagamento;
import joao.alves.delivery.marketplace.domain.Pedido;
import joao.alves.delivery.marketplace.dto.request.PagamentoRequest;
import joao.alves.delivery.marketplace.dto.response.PagamentoResponse;
import joao.alves.delivery.marketplace.mapper.PagamentoMapper;
import joao.alves.delivery.marketplace.repository.PagamentoRepository;
import joao.alves.delivery.marketplace.repository.PedidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, PedidoRepository pedidoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public PagamentoResponse cadastrar(PagamentoRequest pagamentoRequest) {
        Pedido pedido = pedidoRepository.findById(pagamentoRequest.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

        if (pedido.getStatusPedido() != Pedido.Status.CONFIRMADO) {
            throw new RuntimeException("O pedido precisa estar CONFIRMADO para ser pago.");
        }

        if (pagamentoRequest.statusPagamento() == Pagamento.StatusPagamento.CANCELADO) {
            throw new RuntimeException("Não é possivel cadastrar um pagamento já cancelado;");
        }
        validarValor(pagamentoRequest.valor(), pedido);

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(pagamentoRequest.formaPagamento());
        pagamento.setValor(pagamentoRequest.valor());
        pagamento.setStatusPagamento(pagamentoRequest.statusPagamento());

        Pagamento salvo = pagamentoRepository.save(pagamento);
        avancarPedidoSePago(salvo);
        return PagamentoMapper.toDTO(salvo);
    }

    public Page<PagamentoResponse> listar(Pageable pageable) {
        return pagamentoRepository.findAllBy(pageable);
    }

    public PagamentoResponse atualizar(Long id, PagamentoRequest request) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado."));

        if (pagamento.getStatusPagamento() != Pagamento.StatusPagamento.AGUARDANDO) {
            throw new RuntimeException("Pagamento já finalizado.");
        }
        validarValor(request.valor(), pagamento.getPedido());

        pagamento.setFormaPagamento(request.formaPagamento());
        pagamento.setValor(request.valor());
        pagamento.setStatusPagamento(request.statusPagamento());

        avancarPedidoSePago(pagamento);
        return PagamentoMapper.toDTO(pagamento);
    }

    public void deletar(Long id) {
        pagamentoRepository.deleteById(id);
    }

    private void validarValor(BigDecimal valor, Pedido pedido) {
        if (valor.compareTo(pedido.getTotal()) != 0) {
            throw new RuntimeException("Valor diferente do total do pedido.");
        }
    }

    private void avancarPedidoSePago(Pagamento pagamento) {
        Pedido pedido = pagamento.getPedido();
        if (pagamento.getStatusPagamento() == Pagamento.StatusPagamento.CONCLUIDO
                && pedido.getStatusPedido() == Pedido.Status.CONFIRMADO) {
            pedido.setStatusPedido(Pedido.Status.EM_PREPARO);
        }
    }
}
