package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.ItemPedido;
import joao.alves.delivery.marketplace.dto.response.ItemPedidoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    Page<ItemPedidoResponse> findAllBy(Pageable pageable);

    @Query("select sum(i.quantidade * i.precoUnitario) from ItemPedido i where i.pedido.id = :pedidoId")
    BigDecimal somarTotalPorPedido(@Param("pedidoId") Long pedidoId);
}
