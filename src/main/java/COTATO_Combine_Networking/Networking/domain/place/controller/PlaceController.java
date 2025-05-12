package COTATO_Combine_Networking.Networking.domain.place.controller;

import COTATO_Combine_Networking.Networking.domain.place.dto.request.PlaceCreateRequest;
import COTATO_Combine_Networking.Networking.domain.place.dto.response.PlaceCreateResponse;
import COTATO_Combine_Networking.Networking.domain.place.entity.Place;
import COTATO_Combine_Networking.Networking.domain.place.service.PlaceService;
import COTATO_Combine_Networking.Networking.domain.temp.dto.request.PostCreateRequest;
import COTATO_Combine_Networking.Networking.domain.temp.dto.response.PostResponse;
import COTATO_Combine_Networking.Networking.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    // 위치 등록
    @PostMapping
    public ApiResponse<PlaceCreateResponse> registerPlace(@RequestBody PlaceCreateRequest request) {
        PlaceCreateResponse response= placeService.save(request);
        return ApiResponse.onSuccess(response);
    }

    // 위치 삭제
    @DeleteMapping("/{placeId}")
    public ApiResponse<String> deletePlace(@PathVariable Long placeId) {
        String response = placeService.delete(placeId);
        return ApiResponse.onSuccess(response);
    }

    // 위치 목록 조회
    @GetMapping
    public ApiResponse<List<Place>> getAllPlaces() {
        List<Place> placeList = placeService.findAll();
        return ApiResponse.onSuccess(placeList);
    }

    // 핀 등록
    @PostMapping("/{placeId}/pin")
    public ApiResponse<String> pinPlace(@PathVariable Long placeId) {
        String response = placeService.pin(placeId);
        return ApiResponse.onSuccess(response);
    }

    // 핀 해제
    @DeleteMapping("/{placeId}/pin")
    public ApiResponse<String> unpinPlace(@PathVariable Long placeId) {
        String response = placeService.unpin(placeId);
        return ApiResponse.onSuccess(response);
    }

    /* 위치 상세 조회 (위도, 경도 기반 날씨)
    @GetMapping("/{placeId}")
    public ResponseEntity<WeatherResponse> getPlaceWeather(@PathVariable Long placeId) {
        WeatherResponse weather = placeService.getWeatherForPlace(placeId);
        return ResponseEntity.ok(weather);
    }*/

}
