package COTATO_Combine_Networking.Networking.domain.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import COTATO_Combine_Networking.Networking.domain.dto.request.PostCreateRequest;
import COTATO_Combine_Networking.Networking.domain.dto.response.PostResponse;
import COTATO_Combine_Networking.Networking.domain.service.PostService;
import COTATO_Combine_Networking.Networking.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<PostResponse> create(@RequestBody @Valid PostCreateRequest request) {
        PostResponse response = postService.createPost(request);
        return ApiResponse.onSuccess(response);
    }
}
