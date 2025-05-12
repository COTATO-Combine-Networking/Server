package COTATO_Combine_Networking.Networking.domain.place.controller;

import COTATO_Combine_Networking.Networking.domain.place.dto.request.PlaceCreateRequest;
import COTATO_Combine_Networking.Networking.domain.place.dto.response.PlaceResponse;
import COTATO_Combine_Networking.Networking.domain.place.service.PlaceService;
import COTATO_Combine_Networking.Networking.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    @Operation(summary = "위치 생성 API")
    public ApiResponse<PlaceResponse> registerPlace(@RequestBody PlaceCreateRequest request) {
        PlaceResponse response= placeService.save(request);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/{placeId}")
    @Operation(summary = "위치 삭제 API")
    public ApiResponse<String> deletePlace(@PathVariable Long placeId) {
        String response = placeService.delete(placeId);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/list")
    @Operation(summary = "위치 목록 조회 API")
    public ApiResponse<List<PlaceResponse>> getAllPlaces() {
        List<PlaceResponse> placeList = placeService.findAll();
        return ApiResponse.onSuccess(placeList);
    }

    @PostMapping("/{placeId}/pin")
    @Operation(summary = "위치 핀 등록 API")
    public ApiResponse<String> pinPlace(@PathVariable Long placeId) {
        String response = placeService.pin(placeId);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/{placeId}/pin")
    @Operation(summary = "위치 핀 해제 API")
    public ApiResponse<String> unpinPlace(@PathVariable Long placeId) {
        String response = placeService.unpin(placeId);
        return ApiResponse.onSuccess(response);
    }

    /* 위치 상세 조회 (위도, 경도 기반 날씨)
    @GetMapping("/{placeId}/detail")
    public ResponseEntity<WeatherResponse> getPlaceWeather(@PathVariable Long placeId) {
        WeatherResponse weather = placeService.getWeatherForPlace(placeId);
        return ResponseEntity.ok(weather);
    }*/

}
