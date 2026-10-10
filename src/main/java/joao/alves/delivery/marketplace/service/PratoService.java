package joao.alves.delivery.marketplace.service;

import joao.alves.delivery.marketplace.domain.Prato;
import joao.alves.delivery.marketplace.dto.request.PratoRequest;
import joao.alves.delivery.marketplace.dto.response.PratoResponse;
import joao.alves.delivery.marketplace.mapper.PratoMapper;
import joao.alves.delivery.marketplace.repository.PratoRepository;
import org.springframework.stereotype.Service;

@Service
public class PratoService {

    private final PratoRepository pratoRepository;

    public PratoService(PratoRepository pratoRepository) {
        this.pratoRepository = pratoRepository;
    }

    public PratoResponse cadastrar(PratoRequest pratoRequest) {
        
        Prato entity = PratoMapper.toEntity(pratoRequest);

    }
}
