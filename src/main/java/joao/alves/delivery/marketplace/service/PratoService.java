package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.domain.Prato;
import joao.alves.delivery.marketplace.dto.request.PratoRequest;
import joao.alves.delivery.marketplace.dto.response.PratoResponse;
import joao.alves.delivery.marketplace.mapper.PratoMapper;
import joao.alves.delivery.marketplace.repository.CategoriaRepository;
import joao.alves.delivery.marketplace.repository.PratoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PratoService {

    private final PratoRepository pratoRepository;
    private final CategoriaRepository categoriaRepository;

    public PratoService(PratoRepository pratoRepository, CategoriaRepository categoriaRepository) {
        this.pratoRepository = pratoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public PratoResponse cadastrar(PratoRequest pratoRequest) {
        if (!categoriaRepository.existsById(pratoRequest.categoriaId())) {
            throw new RuntimeException("Categoria não encontrada.");
        }
        return PratoMapper.toDTO(pratoRepository.save(PratoMapper.toEntity(pratoRequest)));
    }

    public Page<PratoResponse> listar(String nome, Pageable pageable) {
        return pratoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public PratoResponse atualizar(Long id, PratoRequest pratoRequest) {
        pratoRepository.findById(id).orElseThrow(() -> new RuntimeException("Prato não encontrado"));
        Prato entity = PratoMapper.toEntity(pratoRequest);
        entity.setId(id);
        Prato save = pratoRepository.save(entity);
        return PratoMapper.toDTO(save);
    }

    public void deletar(Long id) {
        pratoRepository.deleteById(id);
    }

}
