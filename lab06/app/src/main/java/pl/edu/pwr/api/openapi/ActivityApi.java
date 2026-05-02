package pl.edu.pwr.api.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pwr.activity.dto.ActivityDto;
import pl.edu.pwr.activity.dto.CreateActivityRequest;

import java.util.List;

@Tag(name = "Activities", description = "Zarządzanie aktywnościami")
@RequestMapping("/activities")
public interface ActivityApi {

    @Operation(summary = "Pobierz wszystkie aktywności")
    ResponseEntity<List<ActivityDto>> getAllActivities();

    @Operation(summary = "Utwórz aktywność")
    ResponseEntity<ActivityDto> createActivity(@RequestBody CreateActivityRequest activityDto);

}
