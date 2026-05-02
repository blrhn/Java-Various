package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.api.openapi.OrderApi;
import pl.edu.pwr.api.utils.UriCreator;
import pl.edu.pwr.clientorder.ClientOrderService;
import pl.edu.pwr.clientorder.dto.ClientOrderDto;
import pl.edu.pwr.clientorder.dto.CreateClientOrderRequest;
import pl.edu.pwr.clientorder.dto.UpdateClientOrderRequest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements OrderApi {
    private final ClientOrderService clientOrderService;

    @Override
    public ResponseEntity<List<ClientOrderDto>> getAllOrders() {
        return ResponseEntity.ok(clientOrderService.getAllOrders());
    }

    @Override
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        clientOrderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ClientOrderDto> createOrder(@RequestBody CreateClientOrderRequest request) {
        ClientOrderDto createdOrder = clientOrderService.createOrder(request);

        URI location = UriCreator.createURIWithID(createdOrder.id());

        return ResponseEntity.created(location).body(createdOrder);
    }

    @Override
    public ResponseEntity<ClientOrderDto> updateOrder(@PathVariable UUID id, @RequestBody UpdateClientOrderRequest request) {
        return ResponseEntity.ok(clientOrderService.updateOrder(id, request));
    }
}
