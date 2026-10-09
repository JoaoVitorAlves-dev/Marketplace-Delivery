package joao.alves.delivery.marketplace.dto.request;

public record EnderecoRequest(
        Long usuarioId,
        String rua,
        String cidade,
        String estado,
        Long cep
) {
}
