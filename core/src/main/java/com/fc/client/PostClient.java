package com.fc;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// Rest Call 임시 테스트용 클래스
@Component
public class PostClient {
    private final Map<Long, Post> posts = new HashMap<>();

    public PostClient() {
        posts.put(1L, new Post(1L, 1L, "imageUrl1", "content1"));
        posts.put(2L, new Post(2L, 2L, "imageUrl2", "content2"));
        posts.put(3L, new Post(3L, 3L, "imageUrl3", "content3"));
    }

    public Post getPost(Long id) {
        return posts.get(id);
    }
}
