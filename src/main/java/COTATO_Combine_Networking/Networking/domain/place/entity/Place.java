package COTATO_Combine_Networking.Networking.domain.place.entity;

import COTATO_Combine_Networking.Networking.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeName;

    private String addressName;

    private String roadAddressName;

    private String longitude; // x (경도)

    private String latitude; // y (위도)

    @Column(nullable = false)
    private boolean isPinned = false;

    public void setPinned(boolean pinned) {
        this.isPinned = pinned;
    }

    @Builder
    public Place(String placeName, String addressName, String roadAddressName, String longitude, String latitude, boolean isPinned) {
        this.placeName = placeName;
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isPinned = isPinned;
    } ;
}
