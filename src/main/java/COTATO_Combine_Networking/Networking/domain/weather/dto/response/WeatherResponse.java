package COTATO_Combine_Networking.Networking.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

public class WeatherResponse {

    @Data
    @AllArgsConstructor
    public static class DailyWeatherSummary {
        private String date;
        private HalfDayWeather am;
        private HalfDayWeather pm;
    }

    @Data
    @AllArgsConstructor
    public static class HalfDayWeather {
        private String weather;
        private double avgTemp;
        private double pop; // 강수확률 %
    }

}
