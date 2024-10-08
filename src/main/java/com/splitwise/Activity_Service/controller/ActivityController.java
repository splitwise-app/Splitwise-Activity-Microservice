package com.splitwise.Activity_Service.controller;

import com.splitwise.Activity_Service.entity.Activity;
import com.splitwise.Activity_Service.service.ActivityService;
import com.splitwise.Activity_Service.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityService activityService;
    @Autowired
    CacheService cacheService;

    @GetMapping("/getGroupActivities/{groupId}")
    public ResponseEntity<List<Activity>> getGroupActivities(@PathVariable("groupId") Long groupId)
    {
        if(groupId == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Activity> activities = activityService.getActivitiesByGroupId(groupId);
        if(activities == null || activities.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activities,HttpStatus.OK);
    }
    @GetMapping("/evictCache/{groupId}")
    public ResponseEntity<String> evictUserNameCacheByGroupId(@PathVariable("groupId") Long groupId)
    {
        cacheService.evictCacheByKey(groupId);
        return new ResponseEntity<>("Cache invalidated",HttpStatus.OK);
    }
    @GetMapping("/getAllGroupActivities/{userId}")
    public ResponseEntity<List<Activity>> getAllGroupActivitiesOfUser(@PathVariable("userId") Long userId) {
        if (userId == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Activity> activities = activityService.getAllGroupActivitiesOfUser(userId);
        if (activities == null || !activities.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activities,HttpStatus.OK);
    }


}
