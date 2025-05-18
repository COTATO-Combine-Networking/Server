package COTATO_Combine_Networking.Networking.domain.weather.controller;

import COTATO_Combine_Networking.Networking.domain.weather.dto.response.DailyWeatherSummary;
import COTATO_Combine_Networking.Networking.domain.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/5days")
    @Operation(summary = "주간 날씨 조회 API")
    public List<DailyWeatherSummary> getForecast() {
        return weatherService.getFiveDayForecast();
    }
}
