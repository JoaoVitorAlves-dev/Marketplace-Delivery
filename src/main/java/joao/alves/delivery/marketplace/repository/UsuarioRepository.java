package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.Usuario;
import joao.alves.delivery.marketplace.dto.response.UsuarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Boolean existsByUsername(String username);

    Optional<Usuario> findByUsername(String username);

    Page<UsuarioResponse> findAllBy(Pageable pageable);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
