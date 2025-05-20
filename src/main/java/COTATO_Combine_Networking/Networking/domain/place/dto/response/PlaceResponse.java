package COTATO_Combine_Networking.Networking.domain.place.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponse {
    private Long placeId;
    private String placeName;
    private String placeAddress;
    private String longitude;
    private String latitude;
}
