package com.fc.task;

import com.fc.*;
import com.fc.event.LikeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
public class LikeRemoveTask {

    @Autowired
    private PostClient postClient;

    @Autowired
    private NotificationGetService getService;

    @Autowired
    private NotificationRemoveService removeService;

    @Autowired
    private NotificationSaveService saveService;

    public void processEvent(LikeEvent event) {
        Optional<Notification> notificationOpt = getService.getNotificationByTypeAndPostId(NotificationType.LIKE, event.getPostId());

        if (notificationOpt.isEmpty()) {
            log.error("No notification with postId: {}", event.getPostId());
            return;
        }

        LikeNotification notification = (LikeNotification) notificationOpt.get();
        removeLikerAndUpdateNotification(event, notification);
    }

    private void removeLikerAndUpdateNotification(LikeEvent event, LikeNotification notification) {
        notification.removeLiker(event.getUserId(), Instant.now());

        if (notification.getLikerIds().isEmpty()) {
            removeService.deleteById(notification.getId());
        } else {
            saveService.upsert(notification);
        }
    }
}
