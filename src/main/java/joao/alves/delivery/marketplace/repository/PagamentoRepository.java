package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.Pagamento;
import joao.alves.delivery.marketplace.dto.response.PagamentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Page<PagamentoResponse> findAllBy(Pageable pageable);
}
