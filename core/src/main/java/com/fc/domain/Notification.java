package com.fc.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@ToString
@Setter(value = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Document("notifications")
public abstract class Notification {
    @Field(targetType = FieldType.STRING)
    private String id;
    private Long userId;
    private NotificationType type;
    private Instant occurredAt;
    private Instant createAt;
    private Instant lastUpdatedAt;
    private Instant deleteAt;
}
