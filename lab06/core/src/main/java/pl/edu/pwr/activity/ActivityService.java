package pl.edu.pwr.activity;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.pwr.activity.dto.ActivityDto;
import pl.edu.pwr.activity.dto.CreateActivityRequest;
import pl.edu.pwr.client.ClientService;
import pl.edu.pwr.clientorder.ClientOrderRepository;
import pl.edu.pwr.persistence.domain.Activity;
import pl.edu.pwr.persistence.domain.Client;
import pl.edu.pwr.persistence.domain.ClientOrder;

import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ClientService clientService;
    private final ClientOrderRepository clientOrderRepository;

    public ActivityService(
            ActivityRepository activityRepository,
            ClientService clientService,
            ClientOrderRepository clientOrderRepository) {
        this.activityRepository = activityRepository;
        this.clientService = clientService;
        this.clientOrderRepository = clientOrderRepository;
    }

    @Transactional
    public List<ActivityDto> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(a -> new ActivityDto(
                        a.getClient().getId(),
                        a.getOrder().getId(),
                        a.getType(),
                        a.getAmount()
                ))
                .toList();
    }

    @Transactional
    public ActivityDto createActivity(CreateActivityRequest request) {
        Activity activity = new Activity();
        Client client = clientService.getClient(request.clientId());

        ClientOrder order = clientOrderRepository.findById(request.orderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        activity.setClient(client);
        activity.setOrder(order);
        activity.setType(request.type());
        activity.setAmount(request.amount());

        activityRepository.save(activity);

        return new ActivityDto(
                client.getId(),
                order.getId(),
                activity.getType(),
                activity.getAmount()
        );
    }
}
