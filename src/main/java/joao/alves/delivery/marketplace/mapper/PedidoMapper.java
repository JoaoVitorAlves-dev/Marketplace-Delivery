package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.Pedido;
import joao.alves.delivery.marketplace.dto.response.PedidoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PedidoMapper {

    public PedidoResponse toDTO(Pedido pedido) {
            return new PedidoResponse(
                    pedido.getId(),
                    pedido.getUsuario().getId(),
                    pedido.getRestaurante().getId(),
                    pedido.getEndereco().getId(),
                    pedido.getEntregador() != null ? pedido.getEntregador().getId() : null,
                    pedido.getStatusPedido(),
                    pedido.getTotal()
            );
        }
}
