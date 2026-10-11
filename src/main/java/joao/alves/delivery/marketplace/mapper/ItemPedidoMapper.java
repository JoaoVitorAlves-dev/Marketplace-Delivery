package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.ItemPedido;
import joao.alves.delivery.marketplace.domain.Pedido;
import joao.alves.delivery.marketplace.domain.Prato;
import joao.alves.delivery.marketplace.dto.request.ItemPedidoRequest;
import joao.alves.delivery.marketplace.dto.response.ItemPedidoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemPedidoMapper {

    public ItemPedido toEntity(ItemPedidoRequest itemPedidoRequest) {
        ItemPedido itemPedido = new ItemPedido();

        Pedido pedido = new Pedido();
        pedido.setId(itemPedidoRequest.pedidoId());

        Prato prato = new Prato();
        prato.setId(itemPedidoRequest.pratoId());

        itemPedido.setPedido(pedido);
        itemPedido.setPrato(prato);
        itemPedido.setQuantidade(itemPedidoRequest.quantidade());
        return itemPedido;
    }

    public ItemPedidoResponse toDTO(ItemPedido itemPedido) {
        return new ItemPedidoResponse(
                itemPedido.getId(), itemPedido.getPedido().getId(),
                itemPedido.getPrato().getId(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario()
        );
    }

}
