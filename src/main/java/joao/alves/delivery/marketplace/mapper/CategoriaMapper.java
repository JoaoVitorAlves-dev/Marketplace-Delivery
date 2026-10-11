package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.Categoria;
import joao.alves.delivery.marketplace.domain.Prato;
import joao.alves.delivery.marketplace.domain.Restaurante;
import joao.alves.delivery.marketplace.dto.request.CategoriaRequest;
import joao.alves.delivery.marketplace.dto.response.CategoriaResponse;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequest categoriaRequest) {
        Categoria categoria = new Categoria();
        Restaurante restaurante = new Restaurante();
        restaurante.setId(categoriaRequest.restauranteId());
        categoria.setRestaurante(restaurante);
        categoria.setNome(categoriaRequest.nome());
        return categoria;
    }

    public CategoriaResponse toDTO(Categoria categoria) {
        Set<Long> collect = categoria.getPrato().stream()
                .map(Prato::getId)
                .collect(Collectors.toSet());

        return new CategoriaResponse(
                categoria.getId(), categoria.getRestaurante().getId(), collect, categoria.getNome()
        );
    }

}
