package COTATO_Combine_Networking.Networking.domain.service;

import org.springframework.stereotype.Service;

import COTATO_Combine_Networking.Networking.domain.converter.PostConverter;
import COTATO_Combine_Networking.Networking.domain.dto.request.PostCreateRequest;
import COTATO_Combine_Networking.Networking.domain.dto.response.PostResponse;
import COTATO_Combine_Networking.Networking.domain.entity.Post;
import COTATO_Combine_Networking.Networking.domain.respository.PostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponse createPost(PostCreateRequest request) {
        Post post = PostConverter.toEntity(request);
        postRepository.save(post);
        return PostConverter.toResponse(post);
    }
}
