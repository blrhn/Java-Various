package pl.edu.pwr.offer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.pwr.offer.dto.CreateOfferRequest;
import pl.edu.pwr.offer.dto.OfferDto;
import pl.edu.pwr.offer.dto.UpdateOfferRequest;
import pl.edu.pwr.persistence.domain.Offer;

import java.util.List;
import java.util.UUID;

@Service
public class OfferService {
    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<OfferDto> getAllActiveOffers() {
        return offerRepository.findByIsActiveTrue().stream()
                .map(o -> new OfferDto(
                        o.getId(),
                        o.getName(),
                        o.getPrice(),
                        o.getMealType()
                ))
                .toList();
    }

    @Transactional
    public void deleteOffer(UUID id) {
        Offer offer = getOffer(id);

        offer.markAsInactive();
        offerRepository.save(offer);
    }

    @Transactional
    public OfferDto createOffer(CreateOfferRequest request) {
        Offer offer = new Offer();

        offer.setName(request.name());
        offer.setPrice(request.price());
        offer.setMealType(request.mealType());

        offerRepository.save(offer);

        return new OfferDto(
                offer.getId(),
                offer.getName(),
                offer.getPrice(),
                offer.getMealType()
        );
    }

    @Transactional
    public OfferDto updateOffer(UpdateOfferRequest request) {
        Offer offer = getOffer(request.offerId());

        offer.setName(request.name());
        offer.setPrice(request.price());
        offer.setMealType(request.mealType());

        Offer updatedOffer = offerRepository.save(offer);

        return new OfferDto(
                updatedOffer.getId(),
                updatedOffer.getName(),
                updatedOffer.getPrice(),
                updatedOffer.getMealType()
        );
    }

    public Offer getOfferIncludingInactive(UUID id) {
        return offerRepository.findByIdIncludingInactive(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer with id " + id + " not found (even inactive)"));
    }

    public Offer getOffer(UUID id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer with id " + id + " not found"));
    }
}
