package com.fc.service.converter;

import com.fc.client.PostClient;
import com.fc.client.UserClient;
import com.fc.domain.CommentNotification;
import com.fc.domain.Post;
import com.fc.domain.User;
import com.fc.service.dto.ConvertedCommentNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentUserNotificationConverter {

    private final UserClient userClient;
    private final PostClient postClient;

    public ConvertedCommentNotification convert(CommentNotification notification) {
        User user = userClient.getUsers(notification.getWriterId());
        Post post = postClient.getPost(notification.getPostId());

        return new ConvertedCommentNotification(notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),
                notification.getComment(),
                post.getImageUrl()
        );
    }
}
