package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.Categoria;
import joao.alves.delivery.marketplace.domain.Restaurante;
import joao.alves.delivery.marketplace.dto.request.CategoriaRequest;
import joao.alves.delivery.marketplace.dto.response.CategoriaResponse;
import lombok.experimental.UtilityClass;

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
        return new CategoriaResponse(
                categoria.getId(), categoria.getRestaurante().getId(), categoria.getPrato().getId(), categoria.getNome()
        );
    }

}
