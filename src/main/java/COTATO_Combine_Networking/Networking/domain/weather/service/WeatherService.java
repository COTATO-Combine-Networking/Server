package COTATO_Combine_Networking.Networking.domain.weather.service;

import COTATO_Combine_Networking.Networking.domain.weather.dto.request.WeatherRequest;
import COTATO_Combine_Networking.Networking.domain.weather.dto.response.WeatherResponse;
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

    private final String API_KEY = "d364b0258b887a52cfd560c72aef20ac";
    private final String BASE_FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private final String BASE_CURRENT_URL = "https://api.openweathermap.org/data/2.5/weather";

    public List<WeatherResponse.DailyWeatherSummary> getFiveDayForecast() {
        RestTemplate restTemplate = new RestTemplate();

        // Forecast 가져오기
        String forecastUrl = BASE_FORECAST_URL + "?lat=37.5665&lon=126.9780&appid=" + API_KEY + "&units=metric&lang=kr";
        WeatherRequest.ForecastResponse response = restTemplate.getForObject(forecastUrl, WeatherRequest.ForecastResponse.class);

        // 날짜별로 그룹화
        Map<String, List<WeatherRequest.WeatherItem>> grouped = response.getList().stream()
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

        List<WeatherResponse.DailyWeatherSummary> summaries = new ArrayList<>();

        for (String date : targetDates) {
            List<WeatherRequest.WeatherItem> items = grouped.get(date);

            // 오전
            List<WeatherRequest.WeatherItem> am = items.stream()
                    .filter(i -> getHour(i) < 12)
                    .toList();

            // 오후
            List<WeatherRequest.WeatherItem> pm = items.stream()
                    .filter(i -> getHour(i) >= 12)
                    .toList();

            WeatherResponse.HalfDayWeather amWeather;

            // 오전 데이터가 없고 오늘이면, 현재 날씨로 대체하고 강수확률은 pm에서
            if (date.equals(today.toString())&& am.isEmpty()) {
                String currentUrl = BASE_CURRENT_URL + "?lat=37.5665&lon=126.9780&appid=" + API_KEY + "&units=metric&lang=kr";
                WeatherRequest.CurrentWeather current = restTemplate.getForObject(currentUrl, WeatherRequest.CurrentWeather.class);

                // pm의 강수확률
                double pop = summarizeHalfDay(pm).getPop();

                amWeather = new WeatherResponse.HalfDayWeather(
                        current.getWeather().get(0).getMain(),
                        current.getMain().getTemp(),
                        pop
                );
            } else {
                amWeather = summarizeHalfDay(am);
            }

            summaries.add(new WeatherResponse.DailyWeatherSummary(
                    date,
                    amWeather,
                    summarizeHalfDay(pm)
            ));
        }

        return summaries;
    }

    private int getHour(WeatherRequest.WeatherItem item) {
        return Integer.parseInt(item.getDt_txt().substring(11, 13));
    }

    private WeatherResponse.HalfDayWeather summarizeHalfDay(List<WeatherRequest.WeatherItem> items) {
        if (items.isEmpty()) return new WeatherResponse.HalfDayWeather("정보 없음", 0, 0);

        double avgTemp = items.stream().mapToDouble(i -> i.getMain().getTemp()).average().orElse(0);
        double avgPop = items.stream().mapToDouble(i -> i.getPop() * 100).average().orElse(0);
        String mostCommonWeather = items.get(0).getWeather().get(0).getMain();

        return new WeatherResponse.HalfDayWeather(mostCommonWeather, avgTemp, avgPop);
    }
}
