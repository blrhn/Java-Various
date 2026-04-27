package pl.edu.pwr.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pwr.clientorder.ClientOrderRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final ClientOrderRepository orderRepository;

    public BigDecimal calculateTotalIncome() {
        return orderRepository.calculateTotalIncome().orElse(BigDecimal.ZERO);
    }
}
