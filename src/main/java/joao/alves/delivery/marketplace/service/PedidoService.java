package joao.alves.delivery.marketplace.service;

import jakarta.transaction.Transactional;
import joao.alves.delivery.marketplace.domain.Endereco;
import joao.alves.delivery.marketplace.domain.Pedido;
import joao.alves.delivery.marketplace.domain.Restaurante;
import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.dto.request.PedidoRequest;
import joao.alves.delivery.marketplace.dto.request.PedidoStatusRequest;
import joao.alves.delivery.marketplace.dto.response.PedidoResponse;
import joao.alves.delivery.marketplace.mapper.PedidoMapper;
import joao.alves.delivery.marketplace.repository.EnderecoRepository;
import joao.alves.delivery.marketplace.repository.PedidoRepository;
import joao.alves.delivery.marketplace.repository.RestauranteRepository;
import joao.alves.delivery.marketplace.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RestauranteRepository restauranteRepository;

    public PedidoService(PedidoRepository pedidoRepository, EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository, RestauranteRepository restauranteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @Transactional
    public PedidoResponse cadastrar(PedidoRequest request) {
        Endereco endereco = enderecoRepository.findById(request.enderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado."));
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado."));
        Restaurante restaurante = restauranteRepository.findById(request.restauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado."));

        if (!endereco.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("O endereço não pertence ao usuário.");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEndereco(endereco);
        pedido.setRestaurante(restaurante);
        return PedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    public Page<PedidoResponse> listar(Pageable pageable) {
        return pedidoRepository.findAllBy(pageable);
    }

    @Transactional
    public PedidoResponse atualizar(Long id, PedidoStatusRequest request) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

        Pedido.Status atual = pedido.getStatusPedido();
        Pedido.Status novo = request.status();

        if (novo.ordinal() != atual.ordinal() + 1) {
            throw new RuntimeException("Transição inválida: " + atual + " -> " + novo + ".");
        }
        if (novo == Pedido.Status.CONFIRMADO && pedido.getTotal().signum() == 0) {
            throw new RuntimeException("Pedido sem itens.");
        }
        if (novo == Pedido.Status.EM_ENTREGA) {
            if (request.entregadorId() == null) {
                throw new RuntimeException("Informe o entregador.");
            }
            Usuario entregador = usuarioRepository.findById(request.entregadorId())
                    .orElseThrow(() -> new RuntimeException("Entregador não encontrado."));
            if (!"ENTREGADOR".equals(entregador.getRole().getNome())) {
                throw new RuntimeException("O usuário informado não é entregador.");
            }
            pedido.setEntregador(entregador);
        }

        pedido.setStatusPedido(novo);
        return PedidoMapper.toDTO(pedidoRepository.save(pedido));
        }

    @Transactional
    public void deletar(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido não encontrado.");
        }
        pedidoRepository.deleteById(id);
    }

}
