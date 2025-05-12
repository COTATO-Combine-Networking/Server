package COTATO_Combine_Networking.Networking.domain.place.converter;

import COTATO_Combine_Networking.Networking.domain.place.dto.request.PlaceCreateRequest;
import COTATO_Combine_Networking.Networking.domain.place.dto.response.PlaceResponse;
import COTATO_Combine_Networking.Networking.domain.place.entity.Place;

public class PlaceConverter {
    public static Place toEntity(PlaceCreateRequest request) {
        return Place.builder()
                .placeName(request.getPlaceName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
    }

    public static PlaceResponse toResponse(Place place) {
        return PlaceResponse.builder()
                .placeId(place.getId())
                .placeName(place.getPlaceName())
                .build();
    }
}

