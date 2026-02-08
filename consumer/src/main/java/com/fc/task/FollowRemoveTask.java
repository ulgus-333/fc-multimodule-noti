package com.fc.task;

import com.fc.NotificationGetService;
import com.fc.NotificationRemoveService;
import com.fc.NotificationType;
import com.fc.event.FollowEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FollowRemoveTask {

    private final NotificationGetService getService;
    private final NotificationRemoveService removeService;

    public void processEvent(FollowEvent event) {
        getService.getNotificationByTypeAndUserIdAndFollowerId(NotificationType.FOLLOW, event.getTargetUserId(), event.getUserId())
                .ifPresent(
                        notification -> removeService.deleteById(notification.getId())
                );
    }
}
