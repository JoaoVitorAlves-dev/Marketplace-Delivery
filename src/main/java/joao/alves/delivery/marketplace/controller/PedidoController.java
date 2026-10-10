package joao.alves.delivery.marketplace.controller;

import jakarta.validation.Valid;
import joao.alves.delivery.marketplace.dto.request.PedidoRequest;
import joao.alves.delivery.marketplace.dto.response.PedidoResponse;
import joao.alves.delivery.marketplace.service.PedidoService;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<PedidoResponse> cadastrar(@NonNull @Valid @RequestBody PedidoRequest pedidoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.cadastrar(pedidoRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<PedidoResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(pedidoService.listar(pageable));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PedidoResponse> atualizar(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody PedidoRequest pedidoRequest) {
        return ResponseEntity.ok(pedidoService.atualizar(id, pedidoRequest));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@NonNull Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
