package joao.alves.delivery.marketplace.controller;

import jakarta.validation.Valid;
import joao.alves.delivery.marketplace.dto.request.PratoRequest;
import joao.alves.delivery.marketplace.dto.response.PratoResponse;
import joao.alves.delivery.marketplace.service.PratoService;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pratos")
public class PratoController {

    private final PratoService pratoService;

    public PratoController(PratoService pratoService) {
        this.pratoService = pratoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<PratoResponse> cadastrar(@NonNull @Valid @RequestBody PratoRequest pratoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pratoService.cadastrar(pratoRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<PratoResponse>> listar(@NonNull @RequestParam String nome) {
        return ResponseEntity.ok(pratoService.listar(nome));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PratoResponse> atualizar(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody PratoRequest pratoRequest) {
        return ResponseEntity.ok(pratoService.atualizar(id, pratoRequest));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@NonNull @PathVariable Long id) {
        pratoService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
