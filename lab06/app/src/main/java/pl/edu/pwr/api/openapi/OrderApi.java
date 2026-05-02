package pl.edu.pwr.api.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.clientorder.dto.ClientOrderDto;
import pl.edu.pwr.clientorder.dto.CreateClientOrderRequest;
import pl.edu.pwr.clientorder.dto.UpdateClientOrderRequest;

import java.util.List;
import java.util.UUID;

@Tag(name = "Orders", description = "Zarządzanie zamówieniami")
@RequestMapping("/orders")
public interface OrderApi {
    @Operation(summary = "Pobierz wszystkie zamówienia")
    @GetMapping
    ResponseEntity<List<ClientOrderDto>> getAllOrders();

    @Operation(summary = "Usuń zamowienie (soft delete)")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable UUID id);

    @Operation(summary = "Utwórz zamówienia")
    @PostMapping
    ResponseEntity<ClientOrderDto> createOrder(@RequestBody CreateClientOrderRequest request);

    @Operation(summary = "Zauktualizuj zamówienie")
    @PutMapping("/{id}")
    ResponseEntity<ClientOrderDto> updateOrder(@PathVariable UUID id, @RequestBody UpdateClientOrderRequest request);
}
