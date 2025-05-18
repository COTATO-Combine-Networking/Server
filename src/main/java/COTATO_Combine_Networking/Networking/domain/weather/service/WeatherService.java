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
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";


    public List<WeatherResponse.DailyWeatherSummary> getFiveDayForecast() {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "?lat=37.5665&lon=126.9780&appid=" + API_KEY + "&units=metric&lang=kr";

        WeatherRequest.ForecastResponse response = restTemplate.getForObject(url, WeatherRequest.ForecastResponse.class);

        // 날짜별 데이터 그룹화
        Map<String, List<WeatherRequest.WeatherItem>> grouped = response.getList().stream()
                .collect(Collectors.groupingBy(item -> item.getDt_txt().substring(0, 10)));

        /*
        List<WeatherResponse.DailyWeatherSummary> summaries = new ArrayList<>();

        for (String date : grouped.keySet().stream().sorted().limit(5).toList()) {
            List<WeatherRequest.WeatherItem> items = grouped.get(date);

            // 오전
            List<WeatherRequest.WeatherItem> am = items.stream()
                    .filter(i -> getHour(i) < 12)
                    .toList();

            // 오후
            List<WeatherRequest.WeatherItem> pm = items.stream()
                    .filter(i -> getHour(i) >= 12)
                    .toList();

            summaries.add(new WeatherResponse.DailyWeatherSummary(
                    date,
                    summarizeHalfDay(am),
                    summarizeHalfDay(pm)
            ));
        }*/

        LocalDate today = LocalDate.now();

        // 오늘부터 이후 5일만 필터링
        List<String> targetDates = grouped.keySet().stream()
                .map(LocalDate::parse)
                .filter(date -> !date.isBefore(today)) // 오늘 이후
                .sorted()
                .limit(5)
                .map(LocalDate::toString)
                .toList();

        List<WeatherResponse.DailyWeatherSummary> summaries = new ArrayList<>();

        for (String date : targetDates) {
            List<WeatherRequest.WeatherItem> items = grouped.get(date);

            List<WeatherRequest.WeatherItem> am = items.stream()
                    .filter(i -> getHour(i) < 12)
                    .toList();

            List<WeatherRequest.WeatherItem> pm = items.stream()
                    .filter(i -> getHour(i) >= 12)
                    .toList();

            summaries.add(new WeatherResponse.DailyWeatherSummary(
                    date,
                    summarizeHalfDay(am),
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
        String mostCommonWeather = items.get(0).getWeather().get(0).getMain(); // 간단히 첫 번째로

        return new WeatherResponse.HalfDayWeather(mostCommonWeather, avgTemp, avgPop);
    }
}

