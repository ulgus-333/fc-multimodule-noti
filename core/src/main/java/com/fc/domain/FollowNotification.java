package com.fc;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;

@Getter
@TypeAlias("FollowNotifications")
public class FollowNotification extends Notification {
    private final Long followerId;

    public FollowNotification(String id, Long userId, NotificationType type, Instant occurredAt, Instant createAt, Instant lastUpdatedAt, Instant deleteAt, Long followerId) {
        super(id, userId, type, occurredAt, createAt, lastUpdatedAt, deleteAt);
        this.followerId = followerId;
    }
}
