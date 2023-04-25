package EGA.api.Repository;
import EGA.api.Entity.Client;
import EGA.api.Entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompteRepository extends JpaRepository<Compte, String>   {
    @Query("SELECT c FROM Compte c WHERE c.client.id=:id")
    List<Compte> findByIdClient(@Param("id") Integer id);
}

//Faut regarder ton message sur WhatsApp
