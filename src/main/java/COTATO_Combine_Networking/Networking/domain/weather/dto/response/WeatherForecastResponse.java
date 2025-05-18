package COTATO_Combine_Networking.Networking.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecastResponse {
    private List<WeatherItem> list;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherItem {
        private Main main;
        private List<Weather> weather;
        private double pop; // 0.0 ~ 1.0
        private String dt_txt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Main {
        private double temp;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weather {
        private String main;
        private String description;
    }
}

