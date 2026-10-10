package joao.alves.delivery.marketplace.controller;

import jakarta.validation.Valid;
import joao.alves.delivery.marketplace.dto.request.PagamentoRequest;
import joao.alves.delivery.marketplace.dto.response.PagamentoResponse;
import joao.alves.delivery.marketplace.service.PagamentoService;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<PagamentoResponse> cadastrar(@NonNull @Valid @RequestBody PagamentoRequest pagamentoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.cadastrar(pagamentoRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<PagamentoResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(pagamentoService.listar(pageable));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PagamentoResponse> atualizar(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody PagamentoRequest pagamentoRequest) {
        return ResponseEntity.ok(pagamentoService.atualizar(id, pagamentoRequest));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@NonNull @PathVariable Long id) {
        pagamentoService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
