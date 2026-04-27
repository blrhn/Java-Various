package pl.edu.pwr.persistence.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pwr.persistence.BaseEntity;

@Entity
public class OrderItem extends BaseEntity {
    @Getter
    @Setter
    @ManyToOne
    private ClientOrder order;

    @Getter
    @Setter
    @ManyToOne
    private Offer offer;

    @Getter
    @Setter
    private Integer quantity;
}
