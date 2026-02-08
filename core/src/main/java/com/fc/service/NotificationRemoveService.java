package com.fc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationRemoveService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void deleteById(String id) {
        notificationRepository.deleteById(id);
        log.info("Delete: {}" ,id);
    }
}
