package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.activity.ActivityService;
import pl.edu.pwr.activity.dto.ActivityDto;
import pl.edu.pwr.activity.dto.CreateActivityRequest;

import java.util.List;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping("/all")
    public List<ActivityDto> getAllActivities() {
        return activityService.getAllActivities();
    }

    @PostMapping("/")
    public ActivityDto createActivity(@RequestBody CreateActivityRequest activityDto) {
        return activityService.createActivity(activityDto);
    }
}
