package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.domain.Restaurante;
import joao.alves.delivery.marketplace.dto.request.RestauranteRequest;
import joao.alves.delivery.marketplace.dto.response.RestauranteResponse;
import joao.alves.delivery.marketplace.mapper.RestauranteMapper;
import joao.alves.delivery.marketplace.repository.RestauranteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public RestauranteResponse cadastrar(RestauranteRequest restauranteRequest) {
        Restaurante save = restauranteRepository.save(RestauranteMapper.toEntity(restauranteRequest));
        return RestauranteMapper.toDTO(save);
    }

    public Page<RestauranteResponse> listar(Pageable pageable) {
        return restauranteRepository.findAllBy(pageable);
    }

    public RestauranteResponse atualizar(Long id, RestauranteRequest restauranteRequest) {
        restauranteRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurante não encontrado."));
        Restaurante entity = RestauranteMapper.toEntity(restauranteRequest);
        entity.setId(id);
        restauranteRepository.save(entity);
        return RestauranteMapper.toDTO(entity);
    }

    public void deletar(Long id) {
        restauranteRepository.deleteById(id);
    }
}
