package com.fc.response;

import com.fc.domain.NotificationType;
import com.fc.service.dto.ConvertedCommentNotification;
import com.fc.service.dto.ConvertedFollowNotification;
import com.fc.service.dto.ConvertedLikeNotification;
import com.fc.service.dto.ConvertedNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public abstract class UserNotificationResponse {
    private String id;
    private NotificationType type;
    private Instant occurredAt;

    public static UserNotificationResponse of(ConvertedNotification notification) {
        return switch (notification.getType()) {
            case COMMENT -> CommentUserNotificationResponse.of((ConvertedCommentNotification) notification);
            case LIKE -> LikeUserNotificationResponse.of((ConvertedLikeNotification) notification);
            case FOLLOW -> FollowUserNotificationResponse.of((ConvertedFollowNotification) notification);
        };
    }
}
