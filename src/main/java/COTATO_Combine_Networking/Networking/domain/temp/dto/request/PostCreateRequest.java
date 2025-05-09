package COTATO_Combine_Networking.Networking.domain.temp.dto.request;

import COTATO_Combine_Networking.Networking.domain.temp.enums.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    private String title;
    private String content;
    private PostCategory category;
}
