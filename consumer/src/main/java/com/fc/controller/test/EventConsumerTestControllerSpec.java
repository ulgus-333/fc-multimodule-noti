package com.fc.controller.test;

import com.fc.event.CommentEvent;
import com.fc.event.FollowEvent;
import com.fc.event.LikeEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;

@Tag(name = "Event Consumer 호출 테스트 API")
public interface EventConsumerTestControllerSpec {

    @Operation(
            requestBody = @RequestBody(
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(name = "댓글 이벤트", value = COMMENT_EVENT_PAYLOAD)
                                    }
                            )
                    }
            )
    )
    void comment(CommentEvent event);

    String COMMENT_EVENT_PAYLOAD = """
            {
                "type": "ADD",
                "postId": 1,
                "userId": 2,
                "commentId": 3
            }
            """;

    @Operation(
            requestBody = @RequestBody(
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(name = "게시글 좋아요 이벤트", value = LIKE_EVENT_PAYLOAD)
                                    }
                            )
                    }
            )
    )
    void like(LikeEvent event);

    String LIKE_EVENT_PAYLOAD = """
            {
                "type": "ADD",
                "postId": 1,
                "userId": 2,
                "createAt": "2026-02-08T15:00:00.000Z"
            }
            """;

    @Operation(
            requestBody = @RequestBody(
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(name = "팔로우 이벤트", value = FOLLOW_EVENT_PAYLOAD)
                                    }
                            )
                    }
            )
    )
    void follow(FollowEvent event);

    String FOLLOW_EVENT_PAYLOAD = """
            {
                "type": "ADD",
                "userId": 1,
                "targetUserId": 2,
                "createAt": "2026-02-08T15:00:00.000Z"
            }
            """;
}
