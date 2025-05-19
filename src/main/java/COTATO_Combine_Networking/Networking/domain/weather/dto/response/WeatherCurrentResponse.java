package COTATO_Combine_Networking.Networking.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherCurrentResponse {
    private WeatherForecastResponse.Main main;
    private List<WeatherForecastResponse.Weather> weather;
}

