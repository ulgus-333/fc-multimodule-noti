package com.fc.service;

import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import com.fc.domain.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
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

    public Instant getLatestUpdatedAt(long userId) {
        Optional<Notification> notification = notificationRepository.findFirstByUserIdOrderByLastUpdatedAtDesc(userId);
        return notification
                .map(Notification::getLastUpdatedAt)
                .orElse(null);
    }
}
