package com.fc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootApplication
@SpringBootTest
class NotificationRepositoryMemoryImplTest {

    @Autowired
    private NotificationRepository sut;

    private final Instant now = Instant.now();
    private final Instant deleteAt = Instant.now().plus(90, ChronoUnit.DAYS);

    @Test
    void test_save() {
        sut.save(new CommentNotification("1", 2L, NotificationType.LIKE, now, now, null, deleteAt, 1L, 1L, 1L, "comment"));
        Optional<Notification> notification = sut.findById("1");

        assertTrue(notification.isPresent());
    }

    @Test
    void test_findById() {
        sut.save(new CommentNotification("2", 2L, NotificationType.LIKE, now, now, null, deleteAt, 2L, 2L, 2L, "comment"));
        Optional<Notification> notificationOpt = sut.findById("2");

        CommentNotification notification = (CommentNotification) notificationOpt.orElseThrow();
        assertEquals(notification.getId(), "2");
        assertEquals(notification.getUserId(), 2L);
        assertEquals(notification.getType(), NotificationType.LIKE);
        assertEquals(notification.getOccurredAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getCreateAt().getEpochSecond(), now.getEpochSecond());
        assertNull(notification.getLastUpdatedAt());
        assertEquals(notification.getDeleteAt().getEpochSecond(), deleteAt.getEpochSecond());
        assertEquals(notification.getPostId(), 2L);
        assertEquals(notification.getWriterId(), 2L);
        assertEquals(notification.getComment(), "comment");
    }

    @Test
    void test_deleteById() {
        sut.save(new CommentNotification("3", 2L, NotificationType.LIKE, now, now, null, deleteAt, 3L, 3L, 3L,"comment"));
        sut.deleteById("3");

        Optional<Notification> notificationOpt = sut.findById("3");
        assertFalse(notificationOpt.isPresent());
    }
}