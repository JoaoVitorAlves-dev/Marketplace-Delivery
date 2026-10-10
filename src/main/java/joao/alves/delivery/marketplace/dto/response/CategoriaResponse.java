package joao.alves.delivery.marketplace.dto.response;

public record CategoriaResponse(
        Long id,
        Long restauranteId,
        Long pratoId,
        String nome
) {
}
