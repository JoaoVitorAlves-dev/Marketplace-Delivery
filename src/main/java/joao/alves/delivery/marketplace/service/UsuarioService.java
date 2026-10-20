package joao.alves.delivery.marketplace.service;

import jakarta.transaction.Transactional;
import joao.alves.delivery.marketplace.domain.RefreshToken;
import joao.alves.delivery.marketplace.domain.Role;
import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.dto.request.LoginRequest;
import joao.alves.delivery.marketplace.dto.request.UsuarioRequest;
import joao.alves.delivery.marketplace.dto.response.RefreshTokenResponse;
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
    private final RefreshTokenService refreshTokenService;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, TokenService tokenService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public RefreshTokenResponse refreshToken(String token) {
        RefreshToken access = refreshTokenService.validarERotacionar(token);

        String novo = tokenService.gerarToken(access.getUsuario().getUsername());
        return new RefreshTokenResponse(novo, access.getToken());
    }

    @Transactional
    public UsuarioResponse alterRoles(Long id, String nome) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado."));
        String nomeRole = nome.toUpperCase();

        if (!nomeRole.equals("CLIENTE") && !nomeRole.equals("ADMIN") && !nomeRole.equals("ENTREGADOR") && !nomeRole.equals("DONO_RESTAURANTE") && !nomeRole.equals("CAIXA") && !nomeRole.equals("VENDEDOR")) {
            throw new RuntimeException("Role inválida.");
        }

        Role novaRole = roleRepository.findByNome(nomeRole)
                .orElseGet(() -> {
                    Role nova = new Role();
                    nova.setNome(nomeRole);
                    return roleRepository.save(nova);
                });

        usuario.setRole(novaRole);

        Usuario save = usuarioRepository.save(usuario);

        return UsuarioMapper.toDTO(save);
    }

    @Transactional
    public void cadastrar(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.existsByUsername(usuarioRequest.username())) {
            throw new RuntimeException("Username já existe.");
        }

        Usuario entity = UsuarioMapper.toEntity(usuarioRequest);
        entity.setPassword(passwordEncoder.encode(usuarioRequest.password()));

        Role cliente = roleRepository.findByNome("CLIENTE").orElseGet(() -> {
            Role roleCliente = new Role();
            roleCliente.setNome("CLIENTE");
            return roleRepository.save(roleCliente);
        });

        entity.setRole(cliente);
        usuarioRepository.save(entity);
    }

    @Transactional
    public RefreshTokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        Usuario usuario = usuarioRepository.findByUsername(loginRequest.username()).orElseThrow();

        String access = tokenService.gerarToken(usuario.getUsername());
        String refresh = refreshTokenService.criar(usuario).getToken();

        return new RefreshTokenResponse(access, refresh);
    }

    @Transactional
    public UsuarioResponse atualizarUsuario(Long id, UsuarioRequest usuarioRequest, String usernameAutenticado) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado."));

        if (!usuario.getUsername().equals(usernameAutenticado)) {
            throw new RuntimeException("Você so pode alterar sua propria conta.");
        }

        if (usuarioRepository.existsByUsernameAndIdNot(usuarioRequest.username(), id)) {
            throw new RuntimeException("Username já existe.");
        }

        usuario.setUsername(usuarioRequest.username());
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.password()));

        Usuario save = usuarioRepository.save(usuario);

        return UsuarioMapper.toDTO(save);
    }

    public Page<UsuarioResponse> listar(Pageable pageable) {
        return usuarioRepository.findAllBy(pageable);
    }

    @Transactional
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

}
