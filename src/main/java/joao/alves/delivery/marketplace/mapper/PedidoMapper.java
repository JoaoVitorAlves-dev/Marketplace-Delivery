package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.Endereco;
import joao.alves.delivery.marketplace.domain.Pedido;
import joao.alves.delivery.marketplace.domain.Restaurante;
import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.dto.request.PedidoRequest;
import joao.alves.delivery.marketplace.dto.response.PedidoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PedidoMapper {

    public Pedido toEntity(PedidoRequest pedidoRequest) {
        Pedido pedido = new Pedido();

        Usuario usuario = new Usuario();
        usuario.setId(pedidoRequest.usuarioId());

        Restaurante restaurante = new Restaurante();
        restaurante.setId(pedidoRequest.restauranteId());

        Endereco endereco = new Endereco();
        endereco.setId(pedidoRequest.enderecoId());

        Usuario entregador = new Usuario();
        entregador.setId(pedidoRequest.entregadorId());

        pedido.setUsuario(usuario);
        pedido.setRestaurante(restaurante);
        pedido.setEndereco(endereco);
        pedido.setEntregador(entregador);
        pedido.setStatusPedido(pedidoRequest.statusPedido());
        pedido.setTotal(pedidoRequest.total());
        return pedido;
    }

    public PedidoResponse toDTO(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(), pedido.getUsuario().getId(), pedido.getRestaurante().getId(), pedido.getEndereco().getId(),
                pedido.getEntregador().getId(), pedido.getStatusPedido(), pedido.getTotal()
        );
    }

}
