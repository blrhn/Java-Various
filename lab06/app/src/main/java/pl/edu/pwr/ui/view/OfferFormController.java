package pl.edu.pwr.ui.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.edu.pwr.offer.OfferService;
import pl.edu.pwr.offer.dto.CreateOfferRequest;
import pl.edu.pwr.offer.dto.OfferDto;
import pl.edu.pwr.offer.dto.UpdateOfferRequest;
import pl.edu.pwr.persistence.enums.MealType;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Consumer;

@Component
public class OfferFormController {
    @FXML
    private TextField mealNameTextField;

    @FXML
    private ComboBox<MealType> mealTypeDropdown;

    @FXML
    private TextField priceTextField;

    private UUID currentOfferId = null;
    private OfferDto offerToEdit = null;
    private final OfferService offerService;
    private Consumer<OfferDto> onCreationCallback;
    private Consumer<OfferDto> onEditCallback;

    public  OfferFormController(OfferService offerService) {
        this.offerService = offerService;
    }

    public void prepareForAdd(Consumer<OfferDto> onCreationCallback) {
        this.currentOfferId = null;
        this.offerToEdit = null;
        this.onCreationCallback = onCreationCallback;
    }

    public void prepareForEdit(OfferDto offerToEdit, Consumer<OfferDto> onEditCallback) {
        this.currentOfferId = offerToEdit.id();
        this.offerToEdit = offerToEdit;
        this.onEditCallback = onEditCallback;
    }

    public void initialize() {
        mealTypeDropdown.getItems().setAll(MealType.values());
        if (currentOfferId == null) {
            mealNameTextField.clear();
            mealTypeDropdown.getSelectionModel().clearSelection();
            priceTextField.clear();
        } else {
            mealNameTextField.setText(offerToEdit.name());
            mealTypeDropdown.getSelectionModel().select(offerToEdit.mealType());
            priceTextField.setText(offerToEdit.price().toString());
        }
    }

    @FXML
    void saveOffer(ActionEvent event) {
        if (currentOfferId == null) {
            CreateOfferRequest request = new CreateOfferRequest(
                    mealNameTextField.getText(),
                    new BigDecimal(priceTextField.getText()),
                    mealTypeDropdown.getValue()
            );

            OfferDto offer = offerService.createOffer(request);

            onCreationCallback.accept(offer);
        } else {
            UpdateOfferRequest request = new UpdateOfferRequest(
                    currentOfferId,
                    mealNameTextField.getText(),
                    new BigDecimal(priceTextField.getText()),
                    mealTypeDropdown.getValue()
            );

            OfferDto offer = offerService.updateOffer(request);

            onEditCallback.accept(offer);
        }

    }
}
