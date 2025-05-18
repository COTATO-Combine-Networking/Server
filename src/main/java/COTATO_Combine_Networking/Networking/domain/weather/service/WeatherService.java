package COTATO_Combine_Networking.Networking.domain.weather.service;

import COTATO_Combine_Networking.Networking.domain.weather.dto.response.DailyWeatherSummary;
import COTATO_Combine_Networking.Networking.domain.weather.dto.response.HalfDayWeather;
import COTATO_Combine_Networking.Networking.domain.weather.dto.response.WeatherCurrentResponse;
import COTATO_Combine_Networking.Networking.domain.weather.dto.response.WeatherForecastResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    private final String API_KEY = "d364b0258b887a52cfd560c72aef20ac";
    private final String BASE_FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private final String BASE_CURRENT_URL = "https://api.openweathermap.org/data/2.5/weather";

    private String buildUrl(String baseUrl) {
        return baseUrl + "?lat=37.5665&lon=126.9780&appid=" + API_KEY + "&units=metric&lang=kr";
    }

    public List<DailyWeatherSummary> getFiveDayForecast() {

        // Forecast API 호출
        String forecastUrl = buildUrl(BASE_FORECAST_URL);
        WeatherForecastResponse forecastResponse = restTemplate.getForObject(forecastUrl, WeatherForecastResponse.class);

        // 날짜별로 그룹화(ex-"2025-05-20")
        Map<String, List<WeatherForecastResponse.WeatherItem>> grouped = forecastResponse.getList().stream()
                .collect(Collectors.groupingBy(item -> item.getDt_txt().substring(0, 10)));

        LocalDate today = LocalDate.now();

        // 오늘부터 이후 5일만 필터링
        List<String> targetDates = grouped.keySet().stream()
                .map(LocalDate::parse)
                .filter(date -> !date.isBefore(today))
                .sorted()
                .limit(5)
                .map(LocalDate::toString)
                .toList();

        List<DailyWeatherSummary> summaries = new ArrayList<>();

        for (String date : targetDates) {
            List<WeatherForecastResponse.WeatherItem> items = grouped.get(date);

            List<WeatherForecastResponse.WeatherItem> am = items.stream()
                    .filter(i -> getHour(i) < 12) // 오전
                    .toList();

            List<WeatherForecastResponse.WeatherItem> pm = items.stream()
                    .filter(i -> getHour(i) >= 12) // 오후
                    .toList();

            HalfDayWeather amWeather;

            // 오전 데이터가 없고 오늘이면 current API 호출, 강수확률은 pm에서
            if (date.equals(today.toString())&& am.isEmpty()) {
                String currentUrl = buildUrl(BASE_CURRENT_URL);
                WeatherCurrentResponse current = restTemplate.getForObject(currentUrl, WeatherCurrentResponse.class);

                double pop = summarizeHalfDay(pm).getPop();

                amWeather = new HalfDayWeather(
                        current.getWeather().get(0).getMain(),
                        current.getMain().getTemp(),
                        pop
                );
            } else {
                amWeather = summarizeHalfDay(am);
            }

            summaries.add(new DailyWeatherSummary(
                    date,
                    amWeather,
                    summarizeHalfDay(pm)
            ));
        }

        return summaries;
    }

    private int getHour(WeatherForecastResponse.WeatherItem item) {
        return Integer.parseInt(item.getDt_txt().substring(11, 13));
    }

    private HalfDayWeather summarizeHalfDay(List<WeatherForecastResponse.WeatherItem> items) {
        if (items.isEmpty()) return new HalfDayWeather("정보 없음", 0, 0);

        double avgTemp = items.stream().mapToDouble(i -> i.getMain().getTemp()).average().orElse(0);
        double avgPop = items.stream().mapToDouble(i -> i.getPop() * 100).average().orElse(0);
        String mostCommonWeather = items.get(0).getWeather().get(0).getMain();

        return new HalfDayWeather(mostCommonWeather, avgTemp, avgPop);
    }
}
