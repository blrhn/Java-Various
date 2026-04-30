package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.offer.OfferService;
import pl.edu.pwr.offer.dto.OfferDto;

import java.util.List;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping("/active")
    public List<OfferDto> getActiveOffers() {
        return offerService.getAllActiveOffers();
    }


}
