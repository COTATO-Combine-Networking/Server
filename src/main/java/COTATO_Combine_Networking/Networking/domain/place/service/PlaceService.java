package COTATO_Combine_Networking.Networking.domain.place.service;

import COTATO_Combine_Networking.Networking.domain.place.converter.PlaceConverter;
import COTATO_Combine_Networking.Networking.domain.place.dto.request.PlaceCreateRequest;
import COTATO_Combine_Networking.Networking.domain.place.dto.response.PlaceResponse;
import COTATO_Combine_Networking.Networking.domain.place.entity.Place;
import COTATO_Combine_Networking.Networking.domain.place.repository.PlaceRepository;
import COTATO_Combine_Networking.Networking.global.apiPayload.code.status.ErrorStatus;
import COTATO_Combine_Networking.Networking.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceResponse save(PlaceCreateRequest request) {

        // 장소 중복 확인
        boolean exists = placeRepository.existsByPlaceNameAndLongitudeAndLatitude(
                request.getPlaceName(), request.getLongitude(), request.getLatitude()
        );
        if (exists) {
            throw new GeneralException(ErrorStatus.PLACE_ALREADY_EXISTS);
        }

        Place place = PlaceConverter.toEntity(request);
        placeRepository.save(place);
        return PlaceConverter.toResponse(place);
    }

    public String delete(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PLACE_NOT_FOUND));

        placeRepository.delete(place);
        return "Place ID " + placeId + " deleted successfully";
    }

    public List<PlaceResponse> findAll() {
        return placeRepository.findAll().stream()
                .map(place -> new PlaceResponse(place.getId(), place.getPlaceName(), place.getAddressName(), place.getLongitude(), place.getLatitude(),place.isPinned()))
                .collect(Collectors.toList());
    }

    public String pin(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PLACE_NOT_FOUND));

        if (place.isPinned()) {
            throw new GeneralException(ErrorStatus.ALREADY_PINNED);
        }

        place.setPinned(true);
        placeRepository.save(place);

        return "Place with ID " + placeId + " is pinned successfully";
    }

    public String unpin(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PLACE_NOT_FOUND));

        if (!place.isPinned()) {
            throw new GeneralException(ErrorStatus.ALREADY_UNPINNED);
        }

        place.setPinned(false);
        placeRepository.save(place);

        return "Place with ID " + placeId + " is unpinned successfully";
    }
}
