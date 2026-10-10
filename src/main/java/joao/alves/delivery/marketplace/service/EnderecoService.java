package joao.alves.delivery.marketplace.service;

import jakarta.transaction.Transactional;
import joao.alves.delivery.marketplace.domain.Endereco;
import joao.alves.delivery.marketplace.dto.request.EnderecoRequest;
import joao.alves.delivery.marketplace.dto.response.EnderecoResponse;
import joao.alves.delivery.marketplace.mapper.EnderecoMapper;
import joao.alves.delivery.marketplace.repository.EnderecoRepository;
import joao.alves.delivery.marketplace.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public EnderecoResponse cadastrarEndereco(EnderecoRequest enderecoRequest) {
        usuarioRepository.findById(enderecoRequest.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado."));
        Endereco endereco = EnderecoMapper.toEntity(enderecoRequest);
        Endereco save = enderecoRepository.save(endereco);
        return EnderecoMapper.toDTO(save);
    }

    public Page<EnderecoResponse> listarEndereco(Pageable pageable) {
        return enderecoRepository.findAllBy(pageable);
    }

    @Transactional
    public EnderecoResponse atualizarEndereco(Long id, EnderecoRequest enderecoRequest) {
        enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado."));
        Endereco endereco = EnderecoMapper.toEntity(enderecoRequest);
        endereco.setId(id);
        Endereco save = enderecoRepository.save(endereco);
        return EnderecoMapper.toDTO(save);
    }

    @Transactional
    public void deletarEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }

}
