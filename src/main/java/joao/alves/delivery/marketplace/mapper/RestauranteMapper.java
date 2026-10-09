package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.Restaurante;
import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.dto.request.RestauranteRequest;
import joao.alves.delivery.marketplace.dto.response.RestauranteResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestauranteMapper {

    public Restaurante toEntity(RestauranteRequest restauranteRequest) {
        Restaurante restaurante = new Restaurante();

        Usuario usuario = new Usuario();
        usuario.setId(restauranteRequest.usuarioId());

        restaurante.setUsuario(usuario);
        restaurante.setNome(restauranteRequest.nome());

        return restaurante;
    }

    public RestauranteResponse toDTO(Restaurante restaurante) {
        return new RestauranteResponse(
                restaurante.getId(), restaurante.getUsuario().getId(), restaurante.getNome()
        );
    }

}
