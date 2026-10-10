package joao.alves.delivery.marketplace.controller;

import jakarta.validation.Valid;
import joao.alves.delivery.marketplace.config.TokenService;
import joao.alves.delivery.marketplace.dto.request.LoginRequest;
import joao.alves.delivery.marketplace.dto.request.UsuarioRequest;
import joao.alves.delivery.marketplace.dto.response.UsuarioResponse;
import joao.alves.delivery.marketplace.service.UsuarioService;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public UsuarioController(UsuarioService usuarioService, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @PutMapping("/alterar/roles/{id}")
    public ResponseEntity<UsuarioResponse> alterarRole(@NonNull @PathVariable Long id, @NonNull @Valid @RequestParam String nome) {
        return ResponseEntity.ok(usuarioService.alterRoles(id, nome));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponse> cadastrar(@NonNull @Valid @RequestBody UsuarioRequest usuarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(usuarioRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@NonNull @Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(usuarioService.login(loginRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<UsuarioResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listar(pageable));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody UsuarioRequest usuarioRequest, @RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        String username = tokenService.validarToken(token);

        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioRequest, username));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@NonNull @PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
