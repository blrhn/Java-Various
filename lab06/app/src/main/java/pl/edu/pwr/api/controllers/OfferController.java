package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.api.openapi.OfferApi;
import pl.edu.pwr.api.utils.UriCreator;
import pl.edu.pwr.offer.OfferService;
import pl.edu.pwr.offer.dto.CreateOfferRequest;
import pl.edu.pwr.offer.dto.OfferDto;
import pl.edu.pwr.offer.dto.UpdateOfferRequest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController implements OfferApi {
    private final OfferService offerService;

    @Override
    public ResponseEntity<List<OfferDto>> getOffers(@RequestParam(required = false, defaultValue = "true") boolean activeOnly) {
        return activeOnly
                ? ResponseEntity.ok(offerService.getAllActiveOffers())
                : ResponseEntity.ok(offerService.getAllOffers());
    }

    @Override
    public ResponseEntity<Void> deleteOffer(@PathVariable UUID id) {
        offerService.deleteOffer(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<OfferDto> createOffer (@RequestBody CreateOfferRequest request) {
        OfferDto createdOffer = offerService.createOffer(request);

        URI location = UriCreator.createURIWithID(createdOffer.id());

        return ResponseEntity.created(location).body(createdOffer);
    }

    @Override
    public ResponseEntity<OfferDto> updateOffer(@PathVariable UUID id, @RequestBody UpdateOfferRequest request) {
        return ResponseEntity.ok(offerService.updateOffer(id, request));
    }
}
