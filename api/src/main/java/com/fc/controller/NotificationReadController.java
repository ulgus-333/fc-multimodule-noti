package com.fc.controller;

import com.fc.response.SetLastReadAtResponse;
import com.fc.service.LastReadAtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/user-notifications")
@RestController
public class NotificationReadController implements NotificationReadControllerSpec {
    private final LastReadAtService lastReadAtService;

    @Override
    @PutMapping("/{userId}/read")
    public SetLastReadAtResponse setLastReadAt(@PathVariable long userId) {
        return new SetLastReadAtResponse(lastReadAtService.setLastReadAt(userId));
    }


}
