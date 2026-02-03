package com.fc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotificationGetService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Optional<Notification> getNotification(NotificationType type, Long commentId) {
        return notificationRepository.findByTypeAndCommentId(type, commentId);
    }
}
