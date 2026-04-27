package pl.edu.pwr.time;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Service
public class FakeTimeService {
    private LocalDateTime fakeTime = LocalDateTime.now();

    public void advanceTime(Duration duration) {
        fakeTime = fakeTime.plus(duration);
    }
}
