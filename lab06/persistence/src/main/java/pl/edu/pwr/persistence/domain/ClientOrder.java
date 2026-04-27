package pl.edu.pwr.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pl.edu.pwr.persistence.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ClientOrder extends BaseEntity {
    @Getter
    @Setter
    @ManyToOne
    private Client  client;

    @Getter
    @Setter
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Getter
    @Setter
    private LocalDateTime deliveryDate;

    @Getter
    @Setter
    private boolean isPaid = false;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private boolean isActive = true;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    private boolean isDelivered = false;

    @Getter
    @Setter
    private boolean reminderSent = false;

    public void markAsInactive() {
        if (this.isActive) {
            this.setActive(false);
        }
    }
}
