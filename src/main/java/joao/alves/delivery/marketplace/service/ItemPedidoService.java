package joao.alves.delivery.marketplace.service;

import jakarta.transaction.Transactional;
import joao.alves.delivery.marketplace.domain.ItemPedido;
import joao.alves.delivery.marketplace.domain.Pedido;
import joao.alves.delivery.marketplace.domain.Prato;
import joao.alves.delivery.marketplace.dto.request.ItemPedidoRequest;
import joao.alves.delivery.marketplace.dto.request.ItemPedidoUpdateRequest;
import joao.alves.delivery.marketplace.dto.response.ItemPedidoResponse;
import joao.alves.delivery.marketplace.mapper.ItemPedidoMapper;
import joao.alves.delivery.marketplace.repository.ItemPedidoRepository;
import joao.alves.delivery.marketplace.repository.PedidoRepository;
import joao.alves.delivery.marketplace.repository.PratoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final PratoRepository pratoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository, PedidoRepository pedidoRepository, PratoRepository pratoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.pratoRepository = pratoRepository;
    }

    public ItemPedidoResponse cadastrar(ItemPedidoRequest itemPedidoRequest) {
        Pedido pedido = pedidoRepository.findById(itemPedidoRequest.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
        Prato prato = pratoRepository.findById(itemPedidoRequest.pratoId())
                .orElseThrow(() -> new RuntimeException("Prato não encontrado."));

        exigirCarrinho(pedido);

        if (prato.getDisponivel() == Prato.Disponivel.INDISPONIVEL) {
            throw new RuntimeException("Prato indisponivel.");
        }
        if (!prato.getCategoria().getRestaurante().getId().equals(pedido.getRestaurante().getId())) {
            throw new RuntimeException("O prato não pertence ao restaurante do pedido.");
        }

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setPrato(prato);
        item.setQuantidade(itemPedidoRequest.quantidade());
        item.setPrecoUnitario(prato.getPreco());

        ItemPedido save = itemPedidoRepository.save(item);
        recalcularTotal(pedido);
        return ItemPedidoMapper.toDTO(save);
    }

    @Transactional
    public Page<ItemPedidoResponse> listar(Pageable pageable) {
        return itemPedidoRepository.findAllBy(pageable);
    }

    @Transactional
    public ItemPedidoResponse atualizar(Long id, ItemPedidoUpdateRequest itemPedidoRequest) {
        ItemPedido item = itemPedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado."));
        exigirCarrinho(item.getPedido());

        item.setQuantidade(itemPedidoRequest.quantidade());
        ItemPedido save = itemPedidoRepository.save(item);
        recalcularTotal(item.getPedido());
        return ItemPedidoMapper.toDTO(save);
    }

    @Transactional
    public void deletar(Long id) {
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        exigirCarrinho(item.getPedido());

        itemPedidoRepository.delete(item);
        recalcularTotal(item.getPedido());
    }

    private void exigirCarrinho(Pedido pedido) {
        if (pedido.getStatusPedido() != Pedido.Status.CARRINHO) {
            throw new RuntimeException("Só é possível alterar itens de um pedido em carrinho.");
        }
    }

    private void recalcularTotal(Pedido pedido) {
        BigDecimal total = itemPedidoRepository.somarTotalPorPedido(pedido.getId());
        pedido.setTotal(total != null ? total : BigDecimal.ZERO);
        pedidoRepository.save(pedido);
    }
}
