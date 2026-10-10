package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.domain.Categoria;
import joao.alves.delivery.marketplace.dto.request.CategoriaRequest;
import joao.alves.delivery.marketplace.dto.response.CategoriaResponse;
import joao.alves.delivery.marketplace.mapper.CategoriaMapper;
import joao.alves.delivery.marketplace.repository.CategoriaRepository;
import joao.alves.delivery.marketplace.repository.RestauranteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final RestauranteRepository restauranteRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, RestauranteRepository restauranteRepository) {
        this.categoriaRepository = categoriaRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public CategoriaResponse cadastrarCategoria(CategoriaRequest categoriaRequest) {
        restauranteRepository.findById(categoriaRequest.restauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado."));
        Categoria entity = CategoriaMapper.toEntity(categoriaRequest);
        Categoria save = categoriaRepository.save(entity);
        return CategoriaMapper.toDTO(save);
    }

    public Page<CategoriaResponse> listarCategoria(Pageable pageable) {
        return categoriaRepository.findAllBy(pageable);
    }

    public CategoriaResponse atualizarCategoria(Long id, CategoriaRequest categoriaRequest) {
        categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
        Categoria entity = CategoriaMapper.toEntity(categoriaRequest);
        entity.setId(id);
        Categoria save = categoriaRepository.save(entity);
        return CategoriaMapper.toDTO(save);
    }

    public void deletarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
