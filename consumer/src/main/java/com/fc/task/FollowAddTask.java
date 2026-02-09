package com.fc.task;

import com.fc.domain.FollowNotification;
import com.fc.utils.NotificationIdGenerator;
import com.fc.service.NotificationSaveService;
import com.fc.domain.NotificationType;
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
