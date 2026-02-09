package com.fc.controller;

import com.fc.response.UserNotificationListResponse;
import com.fc.service.GetUserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RequiredArgsConstructor
@RequestMapping("/v1/user-notifications")
@RestController
public class UserNotificationListController {
    private final GetUserNotificationService notificationService;

    @GetMapping("/{userId}")
    public UserNotificationListResponse getNotifications(@PathVariable Long userId,
                                 @RequestParam(value = "pivot", required = false) Instant pivot) {

        return UserNotificationListResponse.of(notificationService.getUserNotificationByPivot(userId, pivot));
    }
}
