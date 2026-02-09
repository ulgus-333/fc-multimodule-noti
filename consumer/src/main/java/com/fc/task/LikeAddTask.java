package com.fc.task;

import com.fc.client.PostClient;
import com.fc.domain.LikeNotification;
import com.fc.domain.Notification;
import com.fc.domain.NotificationType;
import com.fc.domain.Post;
import com.fc.event.LikeEvent;
import com.fc.service.NotificationGetService;
import com.fc.service.NotificationSaveService;
import com.fc.utils.NotificationIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class LikeAddTask {

    @Autowired
    private PostClient postClient;

    @Autowired
    private NotificationGetService getService;

    @Autowired
    private NotificationSaveService saveService;

    public void processEvent(LikeEvent event) {
        Post post = postClient.getPost(event.getPostId());
        if (post == null) {
            log.error("Post is null with postId: {}", event.getPostId());
            return;
        }

        if (post.getUserId().equals(event.getUserId())) {
            return;
        }

        saveService.upsert(createOrUpdateNotification(post, event));
    }

    private Notification createOrUpdateNotification(Post post, LikeEvent event) {
        Optional<Notification> notification = getService.getNotificationByTypeAndPostId(NotificationType.LIKE, post.getId());

        Instant now = Instant.now();
        Instant retention = now.plus(90, ChronoUnit.DAYS);

        if (notification.isPresent()) {
            return updateNotification((LikeNotification) notification.get(), event, now, retention);
        } else {
            return createNotification(post, event, now, retention);
        }
    }

    private Notification updateNotification(LikeNotification notification, LikeEvent event, Instant now, Instant retention) {
        notification.addLiker(event.getUserId(), event.getCreateAt(), now, retention);
        return notification;

    }

    private Notification createNotification(Post post, LikeEvent event, Instant now, Instant retention) {
        return new LikeNotification(
                NotificationIdGenerator.generateId(),
                post.getUserId(),
                NotificationType.LIKE,
                event.getCreateAt(),
                now,
                now,
                retention,
                post.getId(),
                List.of(event.getUserId())
        );
    }
}
