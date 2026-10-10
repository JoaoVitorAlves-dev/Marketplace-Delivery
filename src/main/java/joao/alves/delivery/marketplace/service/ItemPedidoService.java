package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.domain.ItemPedido;
import joao.alves.delivery.marketplace.dto.request.ItemPedidoRequest;
import joao.alves.delivery.marketplace.dto.response.ItemPedidoResponse;
import joao.alves.delivery.marketplace.mapper.ItemPedidoMapper;
import joao.alves.delivery.marketplace.repository.ItemPedidoRepository;
import joao.alves.delivery.marketplace.repository.PedidoRepository;
import joao.alves.delivery.marketplace.repository.PratoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        pedidoRepository.findById(itemPedidoRequest.pedidoId()).orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
        pratoRepository.findById(itemPedidoRequest.pratoId()).orElseThrow(() -> new RuntimeException("Prato não encontrado."));
        ItemPedido entity = ItemPedidoMapper.toEntity(itemPedidoRequest);
        ItemPedido save = itemPedidoRepository.save(entity);
        return ItemPedidoMapper.toDTO(save);
    }

    public Page<ItemPedidoResponse> listar(Pageable pageable) {
        return itemPedidoRepository.findAllBy(pageable);
    }

    public ItemPedidoResponse atualizar(Long id, ItemPedidoRequest itemPedidoRequest) {
        itemPedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado."));
        ItemPedido entity = ItemPedidoMapper.toEntity(itemPedidoRequest);
        ItemPedido save = itemPedidoRepository.save(entity);
        return ItemPedidoMapper.toDTO(save);
    }

    public void deletar(Long id) {
        itemPedidoRepository.deleteById(id);
    }

}
