package com.fc.task;

import com.fc.FollowNotification;
import com.fc.NotificationIdGenerator;
import com.fc.NotificationSaveService;
import com.fc.NotificationType;
import com.fc.event.FollowEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Component
public class FollowAddTask {

    private final NotificationSaveService saveService;

    public void processEvent(FollowEvent event) {
        Instant now = Instant.now();

        FollowNotification notification = createFollowNotification(event, now);

        saveService.insert(notification);
    }

    @NotNull
    private FollowNotification createFollowNotification(FollowEvent event, Instant now) {
        return new FollowNotification(
                NotificationIdGenerator.generateId(),
                event.getTargetUserId(),
                NotificationType.FOLLOW,
                event.getCreateAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                event.getUserId()
        );
    }
}
