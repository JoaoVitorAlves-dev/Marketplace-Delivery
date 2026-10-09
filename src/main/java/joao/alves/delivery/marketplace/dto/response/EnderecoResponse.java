package joao.alves.delivery.marketplace.dto.response;

public record EnderecoResponse(
        Long id,
        Long usuarioId,
        String rua,
        String cidade,
        String estado,
        Long cep
) {
}
