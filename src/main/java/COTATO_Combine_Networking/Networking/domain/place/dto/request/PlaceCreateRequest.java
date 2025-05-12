package COTATO_Combine_Networking.Networking.domain.place.dto.request;

import COTATO_Combine_Networking.Networking.domain.temp.enums.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceCreateRequest {
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private String longitude;
    private String latitude;
}
