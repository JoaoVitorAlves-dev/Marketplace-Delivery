package joao.alves.delivery.marketplace.controller;

import jakarta.validation.Valid;
import joao.alves.delivery.marketplace.dto.request.LoginRequest;
import joao.alves.delivery.marketplace.dto.request.UsuarioRequest;
import joao.alves.delivery.marketplace.dto.response.RefreshTokenResponse;
import joao.alves.delivery.marketplace.dto.response.UsuarioResponse;
import joao.alves.delivery.marketplace.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PutMapping("/alterar/roles/{id}")
    public ResponseEntity<UsuarioResponse> alterarRole(@PathVariable Long id, @Valid @RequestParam String nome) {
        return ResponseEntity.ok(usuarioService.alterRoles(id, nome));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        usuarioService.cadastrar(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<RefreshTokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(usuarioService.login(loginRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<UsuarioResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listar(pageable));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequest usuarioRequest, Authentication authentication) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioRequest, authentication.getName()));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
