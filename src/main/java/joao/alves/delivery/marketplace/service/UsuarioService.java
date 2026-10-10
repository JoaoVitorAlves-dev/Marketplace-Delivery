package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.config.TokenService;
import joao.alves.delivery.marketplace.domain.Role;
import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.dto.request.LoginRequest;
import joao.alves.delivery.marketplace.dto.request.UsuarioRequest;
import joao.alves.delivery.marketplace.dto.response.UsuarioResponse;
import joao.alves.delivery.marketplace.mapper.UsuarioMapper;
import joao.alves.delivery.marketplace.repository.RoleRepository;
import joao.alves.delivery.marketplace.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, TokenService tokenService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse cadastrar(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.existsByUsername(usuarioRequest.username())) {
            throw new RuntimeException("Username já existe.");
        }

        Usuario entity = UsuarioMapper.toEntity(usuarioRequest);
        entity.setPassword(passwordEncoder.encode(usuarioRequest.password()));

        Role cliente = roleRepository.findByNome("CLIENTE").orElseGet(() -> {
            Role roleUser = new Role();
            roleUser.setNome("CLIENTE");
            return roleRepository.save(roleUser);
        });

        Role donoRestaurante = roleRepository.findByNome("DONO_RESTAURANTE").orElseGet(() -> {
            Role roleUser = new Role();
            roleUser.setNome("DONO_RESTAURANTE");
            return roleRepository.save(roleUser);
        });

        Role admin = roleRepository.findByNome("ADMIN").orElseGet(() -> {
            Role roleUser = new Role();
            roleUser.setNome("ADMIN");
            return roleRepository.save(roleUser);
        });

        entity.setRole(cliente);
        Usuario save = usuarioRepository.save(entity);
        return UsuarioMapper.toDTO(save);
    }

    public String login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                );

        authenticationManager.authenticate(authenticationToken);

        return tokenService.gerarToken(loginRequest.username());
    }

    public Page<UsuarioResponse> listar(Pageable pageable) {
        return usuarioRepository.findAllBy(pageable);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

}
