package COTATO_Combine_Networking.Networking.domain.place.dto.response;

import COTATO_Combine_Networking.Networking.domain.temp.enums.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceCreateResponse {
    private Long placeId;
    private String placeName;
    private String latitude;
    private String longitude;
}
