package com.fc.controller.test;

import com.fc.event.CommentEvent;
import com.fc.event.FollowEvent;
import com.fc.event.LikeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RequiredArgsConstructor
@RestController
public class EventConsumerTestController implements EventConsumerTestControllerSpec {

    private final Consumer<CommentEvent> comment;
    private final Consumer<LikeEvent> like;
    private final Consumer<FollowEvent> follow;

    @PostMapping("/test/comment")
    @Override
    public void comment(@RequestBody CommentEvent event) {
        comment.accept(event);
    }

    @PostMapping("/test/like")
    @Override
    public void like(@RequestBody LikeEvent event) {
        like.accept(event);
    }

    @PostMapping("/test/follow")
    @Override
    public void follow(@RequestBody FollowEvent event) {
        follow.accept(event);
    }

}
