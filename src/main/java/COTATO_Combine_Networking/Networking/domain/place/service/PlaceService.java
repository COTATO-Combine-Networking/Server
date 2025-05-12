package COTATO_Combine_Networking.Networking.domain.place.service;

import COTATO_Combine_Networking.Networking.domain.place.converter.PlaceConverter;
import COTATO_Combine_Networking.Networking.domain.place.dto.request.PlaceCreateRequest;
import COTATO_Combine_Networking.Networking.domain.place.dto.response.PlaceResponse;
import COTATO_Combine_Networking.Networking.domain.place.entity.Place;
import COTATO_Combine_Networking.Networking.domain.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceResponse save(PlaceCreateRequest request) {
        Place place = PlaceConverter.toEntity(request);
        placeRepository.save(place);
        return PlaceConverter.toResponse(place);
    }

    public String delete(Long placeId) {
        placeRepository.deleteById(placeId);
        return "Place ID " + placeId + " deleted successfully";
    }

    public List<PlaceResponse> findAll() {
        return placeRepository.findAll().stream()
                .map(place -> new PlaceResponse(place.getId(), place.getPlaceName()))
                .collect(Collectors.toList());
    }

    public String pin(Long placeId) {
        placeRepository.findById(placeId).ifPresent(place -> {
            place.setPinned(true);
            placeRepository.save(place);
        });

        return "Place with ID " + placeId + " is pinned successfully";
    }

    public String unpin(Long placeId) {
        placeRepository.findById(placeId).ifPresent(place -> {
            place.setPinned(false);
            placeRepository.save(place);
        });

        return "Place with ID " + placeId + " is unpinned successfully";
    }
}
