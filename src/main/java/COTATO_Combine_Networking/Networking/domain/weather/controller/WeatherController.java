package COTATO_Combine_Networking.Networking.domain.weather.controller;

import COTATO_Combine_Networking.Networking.domain.weather.dto.response.WeatherResponse;
import COTATO_Combine_Networking.Networking.domain.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/5days")
    public List<WeatherResponse.DailyWeatherSummary> getForecast() {
        return weatherService.getFiveDayForecast();
    }
}
