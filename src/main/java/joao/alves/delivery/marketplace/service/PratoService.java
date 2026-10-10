package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.domain.Prato;
import joao.alves.delivery.marketplace.dto.request.PratoRequest;
import joao.alves.delivery.marketplace.dto.response.PratoResponse;
import joao.alves.delivery.marketplace.mapper.PratoMapper;
import joao.alves.delivery.marketplace.repository.PratoRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PratoService {

    private final PratoRepository pratoRepository;

    public PratoService(PratoRepository pratoRepository) {
        this.pratoRepository = pratoRepository;
    }

    public PratoResponse cadastrar(PratoRequest pratoRequest) {
        Prato entity = PratoMapper.toEntity(pratoRequest);
        Prato save = pratoRepository.save(entity);
        return PratoMapper.toDTO(save);
    }

    public Page<PratoResponse> listar(String nome) {
        return pratoRepository.findByNome(nome);
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
