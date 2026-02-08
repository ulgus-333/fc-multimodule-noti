package com.fc.task;

import com.fc.client.CommentClient;
import com.fc.client.PostClient;
import com.fc.domain.*;
import com.fc.event.CommentEvent;
import com.fc.service.NotificationSaveService;
import com.fc.utils.NotificationIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
public class CommentAddTask {

    @Autowired
    private PostClient postClient;

    @Autowired
    private CommentClient commentClient;

    @Autowired
    private NotificationSaveService notificationSaveService;

    public void processEvent(CommentEvent event) {
        Post post = postClient.getPost(event.getPostId());
        if (Objects.equals(post.getUserId(), event.getUserId())) {
            return;
        }

        Comment comment = commentClient.getComments(event.getCommentId());

        Notification notification = createNotification(post, comment);
        notificationSaveService.insert(notification);
    }

    private Notification createNotification(Post post, Comment comment) {
        Instant now = Instant.now();
        return new CommentNotification(
                NotificationIdGenerator.generateId(),
                post.getUserId(),
                NotificationType.COMMENT,
                comment.getCreateAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                post.getId(),
                comment.getUserId(),
                comment.getId(),
                comment.getContents()
        );
    }
}
