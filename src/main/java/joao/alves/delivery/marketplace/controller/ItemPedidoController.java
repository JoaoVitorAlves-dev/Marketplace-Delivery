package joao.alves.delivery.marketplace.controller;

import jakarta.validation.Valid;
import joao.alves.delivery.marketplace.dto.request.ItemPedidoRequest;
import joao.alves.delivery.marketplace.dto.response.ItemPedidoResponse;
import joao.alves.delivery.marketplace.service.ItemPedidoService;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("itempedidos")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ItemPedidoResponse> cadastrar(@NonNull @Valid @RequestBody ItemPedidoRequest itemPedidoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemPedidoService.cadastrar(itemPedidoRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<ItemPedidoResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(itemPedidoService.listar(pageable));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ItemPedidoResponse> atualizar(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody ItemPedidoRequest itemPedidoRequest) {
        return ResponseEntity.ok(itemPedidoService.atualizar(id, itemPedidoRequest));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@NonNull @PathVariable Long id) {
        itemPedidoService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
