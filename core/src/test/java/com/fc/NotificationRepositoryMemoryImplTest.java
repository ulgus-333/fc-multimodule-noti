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
        sut.save(new Notification("1", 2L, NotificationType.LIKE, now, deleteAt));
        Optional<Notification> notification = sut.findById("1");

        assertTrue(notification.isPresent());
    }

    @Test
    void test_findById() {
        sut.save(new Notification("2", 2L, NotificationType.LIKE, now, deleteAt));
        Optional<Notification> notificationOpt = sut.findById("2");

        Notification notification = notificationOpt.orElseThrow();
        assertEquals(notification.id, "2");
        assertEquals(notification.userId, 2L);
        assertEquals(notification.type, NotificationType.LIKE);
        assertEquals(notification.createAt.getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.deleteAt.getEpochSecond(), deleteAt.getEpochSecond());
    }

    @Test
    void test_deleteById() {
        sut.save(new Notification("3", 2L, NotificationType.LIKE, now, deleteAt));
        sut.deleteById("3");

        Optional<Notification> notificationOpt = sut.findById("3");
        assertFalse(notificationOpt.isPresent());
    }
}