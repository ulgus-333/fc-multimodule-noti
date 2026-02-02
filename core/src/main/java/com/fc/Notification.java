package com.fc;

import java.time.Instant;

enum NotificationType {
    LIKE,
    COMMENT,
    FOLLOW,
}

public class Notification {
    public String id;
    public Long userId;
    public NotificationType type;
    public Instant createAt;
    public Instant deleteAt;

    public Notification(String id, Long userId, NotificationType type, Instant createAt, Instant deleteAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.createAt = createAt;
        this.deleteAt = deleteAt;
    }
}
