package pl.edu.pwr.api.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.offer.dto.CreateOfferRequest;
import pl.edu.pwr.offer.dto.OfferDto;
import pl.edu.pwr.offer.dto.UpdateOfferRequest;

import java.util.List;
import java.util.UUID;

@Tag(name = "Offers", description = "Zarządzanie ofertami")
@RequestMapping("/offers")
public interface OfferApi {
    @Operation(summary = "Pobierz wszystkie oferty - tylko aktywne lub wszystkie")
    @GetMapping
    ResponseEntity<List<OfferDto>> getOffers(@RequestParam(required = false, defaultValue = "true") boolean activeOnly);

    @Operation(summary = "Usuń ofertę (softdelete)")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOffer(@PathVariable UUID id);

    @Operation(summary = "Utwórz ofertę")
    @PostMapping
    ResponseEntity<OfferDto> createOffer (@RequestBody CreateOfferRequest request);

    @Operation(summary = "Zaktualizuj ofertę")
    @PutMapping("/{id}")
    ResponseEntity<OfferDto> updateOffer(@PathVariable UUID id, @RequestBody UpdateOfferRequest request);
}
