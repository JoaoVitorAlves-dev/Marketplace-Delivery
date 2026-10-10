package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.Prato;
import joao.alves.delivery.marketplace.dto.response.PratoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long> {

    Page<PratoResponse> findByNome(String nome);

}
