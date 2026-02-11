package com.fc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@RequiredArgsConstructor
@Component
public class CheckNewNotificationService {
    private final NotificationGetService getService;
    private final LastReadAtService lastReadAtService;

    public boolean checkNewNotification(long userId) {
        Instant latestUpdatedAt = getService.getLatestUpdatedAt(userId);
        if (latestUpdatedAt == null) {
            return false;
        }

        Instant lastReadAt = lastReadAtService.getLastReadAt(userId);
        if (lastReadAt == null) {
            return true;
        }

        return latestUpdatedAt.isAfter(lastReadAt);
    }
}
