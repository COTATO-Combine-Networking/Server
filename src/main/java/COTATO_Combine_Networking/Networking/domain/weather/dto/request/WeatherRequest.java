package COTATO_Combine_Networking.Networking.domain.weather.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class WeatherRequest {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ForecastResponse {
        private List<WeatherItem> list;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WeatherItem {
        private Main main;
        private List<Weather> weather;
        private double pop; // 강수확률 (0.0~1.0)
        private String dt_txt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Main {
        private double temp;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Weather {
        private String main; // Clear, Clouds 등
        private String description;
    }

}
