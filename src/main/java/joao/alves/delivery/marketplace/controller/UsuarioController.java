package joao.alves.delivery.marketplace.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Transactional
    @PutMapping("/alterar/roles/{id}")
    public ResponseEntity<UsuarioResponse> alterarRole(@NonNull @PathVariable Long id, @NonNull @RequestBody String nome) {
        return ResponseEntity.ok(usuarioService.alterRoles(id, nome));
    }

    @Transactional
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponse> cadastrar(@NonNull @Valid @RequestBody UsuarioRequest usuarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(usuarioRequest));
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<String> login(@NonNull @Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(usuarioService.login(loginRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<UsuarioResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listar(pageable));
    }

    @Transactional
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@NonNull @PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
