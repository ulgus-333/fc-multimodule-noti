package com.fc.service;

import com.fc.domain.CommentNotification;
import com.fc.domain.FollowNotification;
import com.fc.domain.LikeNotification;
import com.fc.service.converter.CommentUserNotificationConverter;
import com.fc.service.converter.FollowUserNotificationConverter;
import com.fc.service.converter.LikeUserNotificationConverter;
import com.fc.service.dto.ConvertedNotification;
import com.fc.service.dto.GetUserNotificationByPivotResult;
import com.fc.service.dto.GetUserNotificationsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GetUserNotificationService {
    private final NotificationListService listService;
    private final CommentUserNotificationConverter commentConverter;
    private final LikeUserNotificationConverter likeConverter;
    private final FollowUserNotificationConverter followConverter;

    public GetUserNotificationsResult getUserNotificationByPivot(long userId, Instant pivot) {
        GetUserNotificationByPivotResult result = listService.getUserNotificationsByPivot(userId, pivot);

        List<ConvertedNotification> convertedNotifications = result.getNotifications().stream()
                .map(notification -> switch (notification.getType()) {
                    case COMMENT -> commentConverter.convert((CommentNotification) notification);
                    case LIKE -> likeConverter.convert((LikeNotification) notification);
                    case FOLLOW -> followConverter.convert((FollowNotification) notification);
                }).toList();

        return new GetUserNotificationsResult(convertedNotifications, result.isHasNext());
    }
}
