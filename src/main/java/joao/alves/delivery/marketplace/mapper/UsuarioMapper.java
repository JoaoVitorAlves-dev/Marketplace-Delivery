package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.dto.request.UsuarioRequest;
import joao.alves.delivery.marketplace.dto.response.UsuarioResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioRequest.username());
        usuario.setPassword(usuarioRequest.password());
        return usuario;
    }

    public UsuarioResponse toDTO(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(), usuario.getUsername()
        );
    }

}
