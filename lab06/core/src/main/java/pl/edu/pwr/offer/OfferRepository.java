package pl.edu.pwr.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.persistence.domain.Offer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
    @Query(value = "SELECT * FROM offer WHERE id = :id", nativeQuery = true)
    Optional<Offer> findByIdIncludingInactive(@Param("id") UUID id);

    List<Offer> findByIsActiveTrue();
}
