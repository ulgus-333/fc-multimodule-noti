package com.fc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@ToString
@Getter
@AllArgsConstructor
@Document("notifications")
public abstract class Notification {
    private String id;
    private Long userId;
    private NotificationType type;
    private Instant occurredAt;
    private Instant createAt;
    private Instant lastUpdatedAt;
    private Instant deleteAt;
}
