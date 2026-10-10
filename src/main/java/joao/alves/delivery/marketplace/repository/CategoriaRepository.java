package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.Categoria;
import joao.alves.delivery.marketplace.dto.response.CategoriaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Page<CategoriaResponse> findAllBy(Pageable pageable);
}
