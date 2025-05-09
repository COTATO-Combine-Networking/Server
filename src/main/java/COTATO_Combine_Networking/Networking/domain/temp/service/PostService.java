package COTATO_Combine_Networking.Networking.domain.temp.service;

import org.springframework.stereotype.Service;

import COTATO_Combine_Networking.Networking.domain.temp.converter.PostConverter;
import COTATO_Combine_Networking.Networking.domain.temp.dto.request.PostCreateRequest;
import COTATO_Combine_Networking.Networking.domain.temp.dto.response.PostResponse;
import COTATO_Combine_Networking.Networking.domain.temp.entity.Post;
import COTATO_Combine_Networking.Networking.domain.temp.respository.PostRepository;
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
