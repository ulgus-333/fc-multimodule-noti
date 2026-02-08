package com.fc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;
import java.util.List;

@Getter
@TypeAlias("LikeNotifications")
public class LikeNotification extends Notification {
    private final Long postId;
    private final List<Long> likerIds;

    public LikeNotification(String id, Long userId, NotificationType type, Instant occurredAt, Instant createAt, Instant lastUpdatedAt, Instant deleteAt, Long postId, List<Long> likerIds) {
        super(id, userId, type, occurredAt, createAt, lastUpdatedAt, deleteAt);
        this.postId = postId;
        this.likerIds = likerIds;
    }

    public void addLiker(Long likerId, Instant occurredAt, Instant now, Instant retention) {
        this.likerIds.add(likerId);
        this.setOccurredAt(occurredAt);
        this.setLastUpdatedAt(now);
        this.setDeleteAt(retention);
    }

    public void removeLiker(Long userId, Instant now) {
        this.likerIds.remove(userId);
        this.setLastUpdatedAt(now);
    }
}
