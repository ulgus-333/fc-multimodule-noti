package com.fc.service;

import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import com.fc.service.dto.GetUserNotificationByPivotResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class NotificationListService {
    private static final int PAGE_SIZE = 20;
    private final NotificationRepository notificationRepository;

    public GetUserNotificationByPivotResult getUserNotificationsByPivot(Long userId, Instant occurredAt) {
        Slice<Notification> result;
        if (occurredAt == null) {
            result = notificationRepository.findAllByUserIdOrderByOccurredAtDesc(userId, PageRequest.of(0, PAGE_SIZE));
        } else {
            result = notificationRepository.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, occurredAt, PageRequest.of(0, PAGE_SIZE));
        }

        return new GetUserNotificationByPivotResult(result.toList(), result.hasNext());
    }
}
