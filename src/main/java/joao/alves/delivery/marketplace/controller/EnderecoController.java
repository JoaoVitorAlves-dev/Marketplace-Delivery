package joao.alves.delivery.marketplace.controller;

import jakarta.validation.Valid;
import joao.alves.delivery.marketplace.dto.request.EnderecoRequest;
import joao.alves.delivery.marketplace.dto.response.EnderecoResponse;
import joao.alves.delivery.marketplace.service.EnderecoService;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping("/cadastrar/endereco")
    public ResponseEntity<EnderecoResponse> cadastrarEndereco(@Valid @NonNull @RequestBody EnderecoRequest enderecoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.cadastrarEndereco(enderecoRequest));
    }

    @GetMapping("/listar/endereco")
    public ResponseEntity<Page<EnderecoResponse>> listarEndereco(Pageable pageable) {
        return ResponseEntity.ok(enderecoService.listarEndereco(pageable));
    }

    @PutMapping("/atualizar/endereco/{id}")
    public ResponseEntity<EnderecoResponse> atualizarEndereco(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody EnderecoRequest enderecoRequest) {
        return ResponseEntity.ok(enderecoService.atualizarEndereco(id, enderecoRequest));
    }

    @DeleteMapping("/deletar/endereco/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable @NonNull Long id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
