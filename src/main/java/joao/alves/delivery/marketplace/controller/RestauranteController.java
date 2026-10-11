package joao.alves.delivery.marketplace.controller;

import jakarta.validation.Valid;
import joao.alves.delivery.marketplace.dto.request.RestauranteRequest;
import joao.alves.delivery.marketplace.dto.response.RestauranteResponse;
import joao.alves.delivery.marketplace.service.RestauranteService;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<RestauranteResponse> cadastrar(@Valid @RequestBody RestauranteRequest restauranteRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.cadastrar(restauranteRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<RestauranteResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(restauranteService.listar(pageable));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<RestauranteResponse> atualizar(@PathVariable Long id,@Valid @RequestBody RestauranteRequest restauranteRequest) {
        return ResponseEntity.ok(restauranteService.atualizar(id, restauranteRequest));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        restauranteService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
