package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.Endereco;
import joao.alves.delivery.marketplace.dto.response.EnderecoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Page<EnderecoResponse> findAllBy(Pageable pageable);
}
