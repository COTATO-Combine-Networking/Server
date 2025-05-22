package COTATO_Combine_Networking.Networking.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherOneCallResponse {

    private Current current;
    private List<Hourly> hourly;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Current {
        private double temp;
        private List<Weather> weather;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weather {
        private String main;
        private String description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hourly {
        private long dt;
        private double temp;
        private List<Weather> weather;
    }
}
