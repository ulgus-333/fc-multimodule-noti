package com.fc.service.converter;

import com.fc.client.UserClient;
import com.fc.domain.FollowNotification;
import com.fc.domain.User;
import com.fc.service.dto.ConvertedFollowNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FollowUserNotificationConverter {

    private final UserClient userClient;

    public ConvertedFollowNotification convert(FollowNotification notification) {
        User user = userClient.getUsers(notification.getFollowerId());
        boolean isFollowing = userClient.getIsFollowing(notification.getFollowerId(), notification.getUserId());

        return new ConvertedFollowNotification(notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),
                isFollowing
        );
    }
}
