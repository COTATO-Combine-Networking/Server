package COTATO_Combine_Networking.Networking.domain.dto.response;

import java.time.LocalDateTime;

import COTATO_Combine_Networking.Networking.domain.enums.PostCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private PostCategory category;
    private LocalDateTime createdAt;
}
