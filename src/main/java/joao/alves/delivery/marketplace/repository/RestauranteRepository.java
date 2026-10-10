package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.Restaurante;
import joao.alves.delivery.marketplace.dto.response.RestauranteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Page<RestauranteResponse> findAllBy(Pageable pageable);
}
