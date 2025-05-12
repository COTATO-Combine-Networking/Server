package COTATO_Combine_Networking.Networking.domain.place.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceCreateRequest {

    @NotBlank(message = "장소 이름은 필수입니다.")
    private String placeName;

    @NotBlank(message = "주소는 필수입니다.")
    private String addressName;

    @NotBlank(message = "도로명 주소는 필수입니다.")
    private String roadAddressName;

    @NotBlank(message = "경도는 필수입니다.")
    private String longitude;

    @NotBlank(message = "위도는 필수입니다.")
    private String latitude;
}
