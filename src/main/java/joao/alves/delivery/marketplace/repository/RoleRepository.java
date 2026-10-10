package joao.alves.delivery.marketplace.repository;

import joao.alves.delivery.marketplace.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNome(String nome);

}
