package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.ItemPedido;
import joao.alves.delivery.marketplace.dto.response.ItemPedidoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    Page<ItemPedidoResponse> findAllBy(Pageable pageable);
}
