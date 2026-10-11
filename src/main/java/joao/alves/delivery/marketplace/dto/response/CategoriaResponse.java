package joao.alves.delivery.marketplace.dto.response;

import java.util.Set;

public record CategoriaResponse(
        Long id,
        Long restauranteId,
        Set<Long> pratoId,
        String nome
) {
}
