package joao.alves.delivery.marketplace.controller;

import jakarta.validation.Valid;
import joao.alves.delivery.marketplace.dto.request.CategoriaRequest;
import joao.alves.delivery.marketplace.dto.response.CategoriaResponse;
import joao.alves.delivery.marketplace.service.CategoriaService;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CategoriaResponse> cadastrarCategoria(@Valid @RequestBody CategoriaRequest categoriaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.cadastrarCategoria(categoriaRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<CategoriaResponse>> listarCategoria(Pageable pageable) {
        return ResponseEntity.ok(categoriaService.listarCategoria(pageable));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CategoriaResponse> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaRequest categoriaRequest) {
        return ResponseEntity.ok(categoriaService.atualizarCategoria(id, categoriaRequest));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
