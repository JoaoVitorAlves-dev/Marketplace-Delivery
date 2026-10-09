package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.domain.Role;
import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.dto.request.UsuarioRequest;
import joao.alves.delivery.marketplace.dto.response.UsuarioResponse;
import joao.alves.delivery.marketplace.mapper.UsuarioMapper;
import joao.alves.delivery.marketplace.repository.RoleRepository;
import joao.alves.delivery.marketplace.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse cadastrar(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.existsByUsername(usuarioRequest.username())) {
            throw new RuntimeException("Username já existe.");
        }

        Usuario entity = UsuarioMapper.toEntity(usuarioRequest);
        entity.setPassword(passwordEncoder.encode(usuarioRequest.password()));

        Role role = roleRepository.findByNome("USER").orElseGet(() -> {
            Role roleUser = new Role();
            roleUser.setNome("USER");
            return roleUser;
        });

        entity.setRole(role);

        Usuario save = usuarioRepository.save(entity);
        return UsuarioMapper.toDTO(save);
    }

    public Page<UsuarioResponse> listar(Pageable pageable) {
        return usuarioRepository.findAllBy(pageable);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

}
