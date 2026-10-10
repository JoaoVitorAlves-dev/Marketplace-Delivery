package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.Pedido;
import joao.alves.delivery.marketplace.dto.response.PedidoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Page<PedidoResponse> findAllBy(Pageable pageable);
}
