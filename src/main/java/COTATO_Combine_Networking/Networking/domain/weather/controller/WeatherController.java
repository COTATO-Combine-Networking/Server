package COTATO_Combine_Networking.Networking.domain.weather.controller;

import COTATO_Combine_Networking.Networking.domain.weather.dto.response.DailyWeatherSummary;
import COTATO_Combine_Networking.Networking.domain.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/5days")
    @CrossOrigin(origins = "*")
    @Operation(
            summary = "5일간 날씨 예보 조회 API",
            description = """
                3시간 단위 OpenWeather 예보 데이터를 기반으로 하루를 오전(am)과 오후(pm)으로 나누어 5일간 요약 정보를 제공합니다.

                - weather: 해당 시간대에서 가장 많이 나타난 날씨 상태
                - avgTemp: 평균 기온 (°C)
                - pop: 강수 확률, 백분율(%)로 환산된 값 (예: 20.0은 20% 확률)
            """
    )
    public List<DailyWeatherSummary> getForecast(
            @Parameter(description = "위도", required = true)
            @RequestParam double lat,

            @Parameter(description = "경도", required = true)
            @RequestParam double lon
    ) {
        return weatherService.getFiveDayForecast(lat, lon);
    }
}
