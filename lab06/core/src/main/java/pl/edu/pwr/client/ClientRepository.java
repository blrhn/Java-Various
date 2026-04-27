package pl.edu.pwr.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.persistence.domain.Client;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    @Query("SELECT DISTINCT o.client FROM ClientOrder o")
    List<Client> findClientsWithOrders();
}
