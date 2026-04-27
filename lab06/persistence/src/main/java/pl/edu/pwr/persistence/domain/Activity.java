package pl.edu.pwr.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pl.edu.pwr.persistence.BaseEntity;
import pl.edu.pwr.persistence.enums.ActivityType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Activity extends BaseEntity {
    @Getter
    @Setter
    @ManyToOne
    private Client client;

    @Getter
    @Setter
    @ManyToOne
    private ClientOrder order;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @CreationTimestamp
    private LocalDateTime activityDate;

    @Getter
    @Setter
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;
}
