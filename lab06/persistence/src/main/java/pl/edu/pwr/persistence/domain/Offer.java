package pl.edu.pwr.persistence.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import pl.edu.pwr.persistence.BaseEntity;
import pl.edu.pwr.persistence.enums.MealType;

import java.math.BigDecimal;

@Entity
@SQLDelete(sql = "UPDATE offer SET is_active = false WHERE id = ?")
public class Offer extends BaseEntity {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private boolean isActive = true;

    public void markAsInactive() {
        if (this.isActive) {
            this.setActive(false);
        }
    }
}
