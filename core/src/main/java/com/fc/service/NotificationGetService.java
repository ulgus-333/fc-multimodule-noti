package com.fc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotificationGetService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Optional<Notification> getNotificationByTypeAndCommentId(NotificationType type, Long commentId) {
        return notificationRepository.findByTypeAndCommentId(type, commentId);
    }

    public Optional<Notification> getNotificationByTypeAndPostId(NotificationType type, Long postId) {
        return notificationRepository.findByTypeAndPostId(type, postId);
    }

    public Optional<Notification> getNotificationByTypeAndUserIdAndFollowerId(NotificationType type, Long userId, Long followerId) {
        return notificationRepository.findByTypeAndUserIdAndFollowerId(type, userId, followerId);
    }
}
