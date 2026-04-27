package pl.edu.pwr.time;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pwr.activity.ActivityRepository;
import pl.edu.pwr.clientorder.ClientOrderRepository;
import pl.edu.pwr.persistence.domain.Activity;
import pl.edu.pwr.persistence.domain.ClientOrder;
import pl.edu.pwr.persistence.enums.ActivityType;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimulationService {
    private final FakeTimeService timeService;
    private final ClientOrderRepository orderRepository;
    private final ActivityRepository activityRepository;

    @Setter
    private Consumer<String> uiLogCallback;

    @Transactional
    public void advanceTime(Duration duration) {
        timeService.advanceTime(duration);
        LocalDateTime now = timeService.getFakeTime();

        logMessage("Aktualny czas: " + now);

        processDeliveries(now);
        processReminders(now);
    }

    private void processDeliveries(LocalDateTime now) {
        List<ClientOrder> ordersToDeliver = orderRepository.findPendingDeliveries(now);

        for (ClientOrder order : ordersToDeliver) {
            order.setDelivered(true);

            Activity activity = new Activity();

            activity.setClient(order.getClient());
            activity.setOrder(order);
            activity.setType(ActivityType.DELIVERY);
            activity.setAmount(BigDecimal.ZERO);

            activityRepository.save(activity);

            log.info("Klient otrzymal zampwienie {}", order.getId());
            logMessage("Dostarczono zamowienie o ID: " + order.getId());
        }
    }

    private void processReminders(LocalDateTime now) {
        LocalDateTime threshold = now.minusHours(24);

        List<ClientOrder> unpaidOrders = orderRepository.findOrdersForReminder(threshold);

        for (ClientOrder order : unpaidOrders) {
            order.setReminderSent(true);

            Activity activity = new Activity();

            activity.setClient(order.getClient());
            activity.setOrder(order);
            activity.setType(ActivityType.REMINDER);
            activity.setAmount(BigDecimal.ZERO);

            activityRepository.save(activity);

            log.warn("Klient zalega z wplata za zamowienie {} zlozone {}", order.getId(), order.getCreatedAt());
            logMessage("Wyslano monit o braku wplaty do zamowienia: " + order.getId());
        }
    }

    private void logMessage(String message) {
        if (uiLogCallback != null) {
            uiLogCallback.accept(message + "\n");
        }
    }
}
