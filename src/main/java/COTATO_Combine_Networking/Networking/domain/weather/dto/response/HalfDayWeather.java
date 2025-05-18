package COTATO_Combine_Networking.Networking.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HalfDayWeather {
    private String weather;
    private double avgTemp;
    private double pop;
}

