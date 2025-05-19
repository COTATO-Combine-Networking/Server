package COTATO_Combine_Networking.Networking.domain.weather.service;

import COTATO_Combine_Networking.Networking.domain.weather.dto.response.DailyWeatherSummary;
import COTATO_Combine_Networking.Networking.domain.weather.dto.response.HalfDayWeather;
import COTATO_Combine_Networking.Networking.domain.weather.dto.response.WeatherCurrentResponse;
import COTATO_Combine_Networking.Networking.domain.weather.dto.response.WeatherForecastResponse;
import COTATO_Combine_Networking.Networking.global.apiPayload.code.status.ErrorStatus;
import COTATO_Combine_Networking.Networking.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api-key}")
    private String apiKey;

    @Value("${weather.forecast-url}")
    private String forecastUrl;

    @Value("${weather.current-url}")
    private String currentUrl;


    private String buildUrl(String baseUrl, double lat, double lon) {
        return baseUrl + "?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric&lang=kr";
    }

    public List<DailyWeatherSummary> getFiveDayForecast(double lat, double lon) {

        // Forecast API 호출
        String forecastRequestUrl = buildUrl(this.forecastUrl, lat, lon);
        WeatherForecastResponse forecastResponse = restTemplate.getForObject(forecastRequestUrl, WeatherForecastResponse.class);

        if (forecastResponse == null) {
            throw new GeneralException(ErrorStatus.API_RESPONSE_EMPTY);
        }

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
                    .filter(i -> i.getHour() < 12) // 오전
                    .toList();

            List<WeatherForecastResponse.WeatherItem> pm = items.stream()
                    .filter(i -> i.getHour() >= 12) // 오후
                    .toList();

            HalfDayWeather amWeather;

            // 오전 데이터가 없고 오늘이면 current API 호출, 강수확률은 pm에서
            if (date.equals(today.toString())&& am.isEmpty()) {
                String currentRequestUrl = buildUrl(currentUrl, lat, lon);
                WeatherCurrentResponse currentResponse = restTemplate.getForObject(currentRequestUrl, WeatherCurrentResponse.class);

                if (currentResponse == null) {
                    throw new GeneralException(ErrorStatus.API_RESPONSE_EMPTY);
                }

                double pop = summarizeHalfDay(pm).getPop();

                amWeather = new HalfDayWeather(
                        currentResponse.getWeather().get(0).getMain(),
                        currentResponse.getMain().getTemp(),
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

    // 날씨 요약 정보 계산
    private HalfDayWeather summarizeHalfDay(List<WeatherForecastResponse.WeatherItem> items) {
        if (items.isEmpty()) return new HalfDayWeather("정보 없음", 0, 0);

        return new HalfDayWeather(
                mostFrequentWeather(items),
                averageTemp(items),
                averagePop(items)
        );
    }

    private String mostFrequentWeather(List<WeatherForecastResponse.WeatherItem> items) {
        return items.stream()
                .map(i -> i.getWeather().get(0).getMain())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()) // 최빈값
                .map(Map.Entry::getKey)
                .orElse("정보 없음");
    }

    private double averageTemp(List<WeatherForecastResponse.WeatherItem> items) {
        return items.stream()
                .mapToDouble(i -> i.getMain().getTemp())
                .average()
                .orElse(0);
    }

    private double averagePop(List<WeatherForecastResponse.WeatherItem> items) {
        return items.stream()
                .mapToDouble(i -> i.getPop() * 100) // % 변환
                .average()
                .orElse(0);
    }

}
