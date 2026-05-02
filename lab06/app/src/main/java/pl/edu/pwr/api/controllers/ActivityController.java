package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.activity.ActivityService;
import pl.edu.pwr.activity.dto.ActivityDto;
import pl.edu.pwr.activity.dto.CreateActivityRequest;
import pl.edu.pwr.api.openapi.ActivityApi;
import pl.edu.pwr.api.utils.UriCreator;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActivityController implements ActivityApi {
    private final ActivityService activityService;

    @Override
    public ResponseEntity<List<ActivityDto>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    @Override
    public ResponseEntity<ActivityDto> createActivity(@RequestBody CreateActivityRequest activityDto) {
        ActivityDto createdActivity =  activityService.createActivity(activityDto);

        URI location = UriCreator.createURIWithID(createdActivity.id());

        return ResponseEntity.created(location).body(createdActivity);
    }
}
