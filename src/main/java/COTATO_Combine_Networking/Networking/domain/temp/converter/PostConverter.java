package COTATO_Combine_Networking.Networking.domain.temp.converter;

import COTATO_Combine_Networking.Networking.domain.temp.dto.request.PostCreateRequest;
import COTATO_Combine_Networking.Networking.domain.temp.dto.response.PostResponse;
import COTATO_Combine_Networking.Networking.domain.temp.entity.Post;

public class PostConverter {
    public static Post toEntity(PostCreateRequest request) {
        return Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .category(request.getCategory())
            .build();
    }

    public static PostResponse toResponse(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .category(post.getCategory())
            .createdAt(post.getCreatedAt())
            .build();
    }
}
