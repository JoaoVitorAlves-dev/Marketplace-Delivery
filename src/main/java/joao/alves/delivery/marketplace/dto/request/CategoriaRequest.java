package joao.alves.delivery.marketplace.dto.request;

public record CategoriaRequest(
        Long restauranteId,
        Long pratoId,
        String nome
) {
}
