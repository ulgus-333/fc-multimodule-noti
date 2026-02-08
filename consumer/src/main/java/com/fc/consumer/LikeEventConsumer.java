package com.fc.consumer;

import com.fc.event.LikeEvent;
import com.fc.event.LikeEventType;
import com.fc.task.LikeAddTask;
import com.fc.task.LikeRemoveTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class LikeEventConsumer {

    @Autowired
    private LikeAddTask likeAddTask;

    @Autowired
    private LikeRemoveTask likeRemoveTask;

    @Bean(name = "like")
    public Consumer<LikeEvent> like() {
        return event -> {
            if (event.getType() == LikeEventType.ADD) {
                likeAddTask.processEvent(event);
            } else if (event.getType() == LikeEventType.REMOVE) {
                likeRemoveTask.processEvent(event);
            }
        };
    }
}
