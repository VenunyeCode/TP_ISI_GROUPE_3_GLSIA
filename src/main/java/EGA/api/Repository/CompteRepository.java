package EGA.api.Repository;
import EGA.api.Entity.Client;
import EGA.api.Entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompteRepository extends JpaRepository<Compte, String>   {
    List<Compte> findByClient(Client client);
}
