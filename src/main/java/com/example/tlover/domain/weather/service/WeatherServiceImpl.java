package com.example.tlover.domain.weather.service;

import com.example.tlover.domain.region.repository.RegionRepository;
import com.example.tlover.domain.weather.Secret.WeatherSecretKey;
import com.example.tlover.domain.weather.dto.WeatherResultContext;
import com.example.tlover.domain.weather.entity.Weather;
import com.example.tlover.domain.weather.repository.WeatherRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.tlover.domain.weather.Secret.WeatherSecretKey.*;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService{

    private final WeatherRepository weatherRepository;
    private final RegionRepository regionRepository;

    @Transactional
    public void saveWeather(){
        System.out.println("날씨조회");
        for(int i= 0; i<AREAKEY.length; i++){
            WeatherResultContext resultContext = new WeatherResultContext();
            resultContext = getWeather(AREAKEY[i],resultContext);

            Optional<Weather> weather = weatherRepository.findByWeatherRegion(resultContext.getWeatherRegion());

            if(weather.isEmpty()){
                weatherRepository.save(resultContext.toEntity());
            }else{
                weather.get().setWeatherTciGrade(resultContext.getWeatherTciGrade());
                weather.get().setKmaTci(resultContext.getKmaTci());
                weather.get().setTime(resultContext.getTime());
                weather.get().setRegion(regionRepository.findByRegionName(resultContext.getWeatherRegion()).get());
            }
        }
    }

    @SneakyThrows
    public WeatherResultContext getWeather(String cityCode, WeatherResultContext weatherResultContext){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        weatherResultContext.setTime(df.format(cal.getTime()));

        String apiUrl = "http://apis.data.go.kr/1360000/TourStnInfoService/getCityTourClmIdx";

        String serviceKey = WeatherSecretKey.WEATHER_SERVICE_KEY;

        String pageNo = "1";	//페이지 번호
        String dataType = "JSON";	//데이터 타입
        String CURRENT_DATE = df.format(cal.getTime());
        String CITY_AREA_ID = cityCode;
        String DAY = "3";


        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("CURRENT_DATE","UTF-8") + "=" + URLEncoder.encode(CURRENT_DATE, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("CITY_AREA_ID","UTF-8") + "=" + URLEncoder.encode(CITY_AREA_ID, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("DAY","UTF-8") + "=" + URLEncoder.encode(DAY, "UTF-8"));

        /*
         * GET방식으로 전송해서 파라미터 받아오기
         */
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result= sb.toString();

        //Json -> List 매핑
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject)parser.parse(result.toString());
        Gson gson = new Gson();
        Map map = new HashMap();
        map = (Map)gson.fromJson(obj, map.getClass());

        LinkedTreeMap treeMap = (LinkedTreeMap) map.get("response");
        List<LinkedTreeMap> items = new ArrayList<>();

        //지역 값 빼오기
        for(int i=0; i<MAPKEY.length; i++){
            if(MAPKEY[i].equals("item")){
                items = (List<LinkedTreeMap>) treeMap.get(MAPKEY[i]);
            }else{
                treeMap = (LinkedTreeMap) treeMap.get(MAPKEY[i]);
            }
        }

        double totalTci=0;
        for(int i=0; i<items.size(); i++){
            totalTci += ((double) items.get(i).get("kmaTci"));
        }
        weatherResultContext.setKmaTci(Math.round((totalTci/items.size())*100)/100.0);

        if(weatherResultContext.getKmaTci() >= 0.35){
            weatherResultContext.setWeatherTciGrade("매우좋음");
        }else if(weatherResultContext.getKmaTci() >= 0.1) {
            weatherResultContext.setWeatherTciGrade("좋음");
        }else if (weatherResultContext.getKmaTci() >= -0.25){
            weatherResultContext.setWeatherTciGrade("보통");
        }else if (weatherResultContext.getKmaTci() <-0.25){
            weatherResultContext.setWeatherTciGrade("나쁨");
        }

        switch (cityCode){
            case "4215000000":
                weatherResultContext.setWeatherRegion("강원도");
                break;
            case "4613000000":
                weatherResultContext.setWeatherRegion("전라남도");
                break;
            case "4513000000":
                weatherResultContext.setWeatherRegion("전라북도");
                break;
            case "4313000000":
                weatherResultContext.setWeatherRegion("충청북도");
                break;
            case "4413100000":
                weatherResultContext.setWeatherRegion("충청남도");
                break;
            case "5011000000":
                weatherResultContext.setWeatherRegion("제주도");
                break;
            case "4822000000":
                weatherResultContext.setWeatherRegion("경상남도");
                break;
            case "4711100000":
                weatherResultContext.setWeatherRegion("경상북도");
                break;
            case "4136000000":
                weatherResultContext.setWeatherRegion("경기도");
                break;
            case "1156000000":
                weatherResultContext.setWeatherRegion("서울");
                break;
        }

        return weatherResultContext;
    }
}
