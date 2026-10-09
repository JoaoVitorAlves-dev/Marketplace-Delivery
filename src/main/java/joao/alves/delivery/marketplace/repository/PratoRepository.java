package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long> {
}
