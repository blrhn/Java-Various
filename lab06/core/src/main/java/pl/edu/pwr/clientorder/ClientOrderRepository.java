package pl.edu.pwr.clientorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.persistence.domain.ClientOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, UUID> {
    @Query("SELECT o FROM ClientOrder o WHERE o.isPaid = true AND o.isDelivered = false AND o.deliveryDate <= :currentTime")
    List<ClientOrder> findPendingDeliveries(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT o FROM ClientOrder o WHERE o.isPaid = false AND o.reminderSent = false AND o.createdAt <= :thresholdTime")
    List<ClientOrder> findOrdersForReminder(@Param("thresholdTime") LocalDateTime thresholdTime);

    @Query("SELECT SUM(o.price) FROM ClientOrder o WHERE o.isPaid = true")
    Optional<BigDecimal> calculateTotalIncome();
}
