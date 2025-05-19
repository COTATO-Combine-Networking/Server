package COTATO_Combine_Networking.Networking.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyWeatherSummary {
    private String date;
    private HalfDayWeather am;
    private HalfDayWeather pm;
}

