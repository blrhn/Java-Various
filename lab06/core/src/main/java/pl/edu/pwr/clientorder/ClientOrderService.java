package pl.edu.pwr.clientorder;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.pwr.activity.ActivityService;
import pl.edu.pwr.activity.dto.CreateActivityRequest;
import pl.edu.pwr.client.ClientService;
import pl.edu.pwr.client.dto.ClientDto;
import pl.edu.pwr.clientorder.dto.*;
import pl.edu.pwr.offer.OfferService;
import pl.edu.pwr.persistence.domain.Client;
import pl.edu.pwr.persistence.domain.ClientOrder;
import pl.edu.pwr.persistence.domain.Offer;
import pl.edu.pwr.persistence.domain.OrderItem;
import pl.edu.pwr.persistence.enums.ActivityType;
import pl.edu.pwr.time.FakeTimeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClientOrderService {
    private final ClientOrderRepository clientOrderRepository;
    private final ClientService clientService;
    private final OfferService offerService;
    private final ActivityService activityService;
    private final FakeTimeService fakeTimeService;

    public ClientOrderService(
            ClientOrderRepository clientOrderRepository,
            ClientService clientService,
            OfferService offerService,
            ActivityService activityService, FakeTimeService fakeTimeService) {
        this.clientOrderRepository = clientOrderRepository;
        this.clientService = clientService;
        this.offerService = offerService;
        this.activityService = activityService;
        this.fakeTimeService = fakeTimeService;
    }

    @Transactional
    public List<ClientOrderDto> getAllOrders() {
        return clientOrderRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public void deleteOrder(UUID id) {
        ClientOrder order = getOrder(id);

        createActivity(new CreateActivityRequest(
                order.getClient().getId(),
                order.getId(),
                ActivityType.CANCELLATION,
                order.getPrice()
        ));

        order.markAsInactive();

        clientOrderRepository.save(order);
    }

    @Transactional
    public ClientOrderDto createOrder(CreateClientOrderRequest request) {
        ClientOrder order = new ClientOrder();
        Client client = clientService.getClient(request.clientId());

        order.setClient(client);
        order.setAddress(request.address());
        order.setDeliveryDate(request.deliveryDate());
        order.setPaid(request.isPaid());
        order.setCreatedAt(fakeTimeService.getFakeTime());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest item : request.items()) {
            Offer offer = offerService.getOffer(item.offerId());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setOffer(offer);
            orderItem.setQuantity(item.quantity());
            orderItems.add(orderItem);

            totalPrice = totalPrice.add(offer.getPrice().multiply(BigDecimal.valueOf(item.quantity())));
        }

        order.setOrderItems(orderItems);
        order.setPrice(totalPrice);

        ClientOrder savedOrder = clientOrderRepository.save(order);

        if (savedOrder.isPaid()) {
            createActivity(new CreateActivityRequest(
                    client.getId(),
                    savedOrder.getId(),
                    ActivityType.PAYMENT,
                    totalPrice
            ));
        }

        return mapToDto(savedOrder);
    }

    @Transactional
    public ClientOrderDto updateOrder(UpdateClientOrderRequest request) {
        ClientOrder order = getOrder(request.orderId());
        boolean wasPaidBeforeUpdate = order.isPaid();

        order.setAddress(request.address());
        order.setDeliveryDate(request.deliveryDate());
        order.setPaid(request.isPaid());

        List<OrderItem> newItems = new ArrayList<>();
        BigDecimal newTotalPrice = BigDecimal.ZERO;

        order.getOrderItems().clear();

        for (OrderItemRequest itemReq : request.items()) {
            Offer offer = offerService.getOfferIncludingInactive(itemReq.offerId());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setOffer(offer);
            orderItem.setQuantity(itemReq.quantity());
            newItems.add(orderItem);

            newTotalPrice = newTotalPrice.add(offer.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())));
        }

        order.getOrderItems().addAll(newItems);
        order.setPrice(newTotalPrice);

        ClientOrder updatedOrder = clientOrderRepository.save(order);

        if (!wasPaidBeforeUpdate && updatedOrder.isPaid()) {
            createActivity(new CreateActivityRequest(
                    updatedOrder.getClient().getId(),
                    updatedOrder.getId(),
                    ActivityType.PAYMENT,
                    newTotalPrice
            ));
        }

        return mapToDto(updatedOrder);
    }

    private ClientOrderDto mapToDto(ClientOrder o) {
        return new ClientOrderDto(
                o.getId(),
                new ClientDto(
                        o.getClient().getId(),
                        o.getClient().getName(),
                        o.getClient().getSurname(),
                        o.getClient().getEmail()
                ),
                o.getAddress(),
                o.getDeliveryDate(),
                o.isPaid(),
                o.getPrice(),
                o.getOrderItems().stream()
                        .map(oi -> new OrderItemDto(
                                oi.getOffer().getId(),
                                oi.getOffer().getName(),
                                oi.getQuantity()
                        ))
                        .toList()
        );
    }

    private void createActivity(CreateActivityRequest request) {
        activityService.createActivity(request);
    }

    public ClientOrder getOrder(UUID id) {
        return clientOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
    }
}
