package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.domain.Pagamento;
import joao.alves.delivery.marketplace.domain.Pedido;
import joao.alves.delivery.marketplace.dto.request.PagamentoRequest;
import joao.alves.delivery.marketplace.dto.response.PagamentoResponse;
import joao.alves.delivery.marketplace.mapper.PagamentoMapper;
import joao.alves.delivery.marketplace.repository.PagamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public PagamentoResponse cadastrar(PagamentoRequest pagamentoRequest) {
        Pagamento entity = PagamentoMapper.toEntity(pagamentoRequest);

        if (entity.getPedido().getStatusPedido().equals(Pedido.Status.CARRINHO)) {
            throw new RuntimeException("Pedido em carrinho.");
        }
        if (entity.getStatusPagamento().equals(Pagamento.StatusPagamento.CANCELADO)) {
            throw new RuntimeException("Pagamento cancelado.");
        }
        if (!pagamentoRequest.valor().equals(entity.getPedido().getTotal())) {
            throw new RuntimeException("Valor diferente do total.");
        }
        entity.getPedido().setStatusPedido(Pedido.Status.EM_PREPARO);
        Pagamento save = pagamentoRepository.save(entity);
        return PagamentoMapper.toDTO(save);
    }

    public Page<PagamentoResponse> listar(Pageable pageable) {
        return pagamentoRepository.findAllBy(pageable);
    }

    public PagamentoResponse atualizar(Long id, PagamentoRequest pagamentoRequest) {
        pagamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("pagamento não encontrado"));
        Pagamento save = pagamentoRepository.save(PagamentoMapper.toEntity(pagamentoRequest));
        return PagamentoMapper.toDTO(save);
    }

    public void deletar(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
