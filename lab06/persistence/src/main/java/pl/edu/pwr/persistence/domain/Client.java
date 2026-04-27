package pl.edu.pwr.persistence.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import pl.edu.pwr.persistence.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE client SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
public class Client extends BaseEntity {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    @OneToMany(mappedBy = "client")
    private List<ClientOrder> orders = new ArrayList<>();

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private boolean isActive = true;

    public void markAsInactive() {
        if (this.isActive) {
            this.setActive(false);
        }
    }
}
