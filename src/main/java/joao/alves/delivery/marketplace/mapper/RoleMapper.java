package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.Role;
import joao.alves.delivery.marketplace.dto.request.RoleRequest;
import joao.alves.delivery.marketplace.dto.response.RoleResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleMapper {

    public Role toEntity(RoleRequest roleRequest) {
        Role role = new Role();
        role.setNome(roleRequest.nome());
        return role;
    }

    public RoleResponse toDTO(Role role) {
        return new RoleResponse(
                role.getId(), role.getNome()
        );
    }

}
