package com.fc.service;

import com.fc.repository.NotificationReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@RequiredArgsConstructor
@Component
public class LastReadAtService {
    private final NotificationReadRepository readRepository;

    public Instant setLastReadAt(long userId) {
        return readRepository.setLastReadAt(userId);
    }

    public Instant getLastReadAt(long userId) {
        return readRepository.getLastReadAt(userId);
    }
}
