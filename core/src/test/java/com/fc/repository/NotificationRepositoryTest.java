package com.fc.repository;

import com.fc.IntegrationTest;
import com.fc.domain.CommentNotification;
import com.fc.domain.Notification;
import com.fc.domain.NotificationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRepositoryTest extends IntegrationTest {

    @Autowired
    private NotificationRepository sut;

    private final Instant now = Instant.now();
    private final Instant deleteAt = Instant.now().plus(90, ChronoUnit.DAYS);

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 5; i++) {
            Instant occurredAt = now.minus(i, ChronoUnit.DAYS);
            sut.save(new CommentNotification(
                    "id-" + i
                    , 1L
                    , NotificationType.COMMENT
                    , occurredAt
                    , now
                    , now
                    , now.plus(90, ChronoUnit.DAYS)
                    , 1L
                    , 2L
                    , 3L
                    , "comment"
            ));
        }
    }

    @AfterEach
    void tearDown() {
        sut.deleteAll();
    }

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

    @Test
    void test_findAllByUserIdOrderByOccurredAtDesc() {
        final Pageable page = PageRequest.of(0, 3);
        Slice<Notification> result = sut.findAllByUserIdOrderByOccurredAtDesc(1L, page);

        assertEquals(3, result.getContent().size());
        assertTrue(result.hasNext());

        Notification first = result.getContent().get(0);
        Notification sec = result.getContent().get(1);
        Notification thr = result.getContent().get(2);

        assertTrue(first.getOccurredAt().isAfter(sec.getOccurredAt()));
        assertTrue(sec.getOccurredAt().isAfter(thr.getOccurredAt()));
    }

    @Test
    void test_findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc_firstQuery() {
        final Pageable page = PageRequest.of(0, 3);
        Slice<Notification> result = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(1L, now, page);

        assertEquals(3, result.getContent().size());
        assertTrue(result.hasNext());

        Notification first = result.getContent().get(0);
        Notification sec = result.getContent().get(1);
        Notification thr = result.getContent().get(2);

        assertTrue(first.getOccurredAt().isAfter(sec.getOccurredAt()));
        assertTrue(sec.getOccurredAt().isAfter(thr.getOccurredAt()));
    }

    @Test
    void test_findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc_secondQueryWithPivot() {
        final Pageable page = PageRequest.of(0, 3);
        Slice<Notification> firstResult = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(1L, now, page);

        assertTrue(firstResult.hasNext());

        Notification last = firstResult.getContent().getLast();
        Instant pivot = last.getOccurredAt();

        Slice<Notification> secondResult = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(1L, pivot, page);

        assertEquals(2, secondResult.getContent().size());
        assertFalse(secondResult.hasNext());

        Notification first = secondResult.getContent().get(0);
        Notification sec = secondResult.getContent().get(1);

        assertTrue(first.getOccurredAt().isAfter(sec.getOccurredAt()));
    }
}