package joao.alves.delivery.marketplace.service;

import jakarta.transaction.Transactional;
import joao.alves.delivery.marketplace.domain.Endereco;
import joao.alves.delivery.marketplace.domain.Pedido;
import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.dto.request.PedidoRequest;
import joao.alves.delivery.marketplace.dto.response.PedidoResponse;
import joao.alves.delivery.marketplace.mapper.PedidoMapper;
import joao.alves.delivery.marketplace.repository.EnderecoRepository;
import joao.alves.delivery.marketplace.repository.PedidoRepository;
import joao.alves.delivery.marketplace.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository, EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PedidoResponse cadastrar(PedidoRequest pedidoRequest) {
        Endereco endereco = enderecoRepository.findById(pedidoRequest.enderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado."));

        Usuario usuario = usuarioRepository.findById(pedidoRequest.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        Pedido entity = PedidoMapper.toEntity(pedidoRequest);

        if (!endereco.getUsuario().equals(usuario)) {
            throw new RuntimeException("Usuarios diferentes.");
        }

        Pedido save = pedidoRepository.save(entity);
        return PedidoMapper.toDTO(save);
    }

    public Page<PedidoResponse> listar(Pageable pageable) {
        return pedidoRepository.findAllBy(pageable);
    }

    @Transactional
    public PedidoResponse atualizar(Long id, PedidoRequest pedidoRequest) {
        pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
        Pedido entity = PedidoMapper.toEntity(pedidoRequest);
        entity.setStatusPedido(pedidoRequest.statusPedido());
        entity.setId(id);
        Pedido save = pedidoRepository.save(entity);
        return PedidoMapper.toDTO(save);
    }

    @Transactional
    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }

}
