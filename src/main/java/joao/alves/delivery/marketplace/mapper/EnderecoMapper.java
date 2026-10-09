package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.Endereco;
import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.dto.request.EnderecoRequest;
import joao.alves.delivery.marketplace.dto.response.EnderecoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnderecoMapper {

    public Endereco toEntity(EnderecoRequest enderecoRequest) {
        Endereco endereco = new Endereco();

        Usuario usuario = new Usuario();
        usuario.setId(enderecoRequest.usuarioId());

        endereco.setUsuario(usuario);
        endereco.setRua(enderecoRequest.rua());
        endereco.setCidade(enderecoRequest.cidade());
        endereco.setCep(enderecoRequest.cep());
        endereco.setEstado(enderecoRequest.estado());

        return endereco;
    }

    public EnderecoResponse toDTO(Endereco endereco) {
        return new EnderecoResponse(
          endereco.getId(), endereco.getUsuario().getId(), endereco.getRua(),
                endereco.getCidade(), endereco.getEstado(), endereco.getCep()
        );
    }

}
