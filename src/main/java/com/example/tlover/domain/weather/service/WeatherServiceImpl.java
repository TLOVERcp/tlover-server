package com.example.tlover.domain.weather.service;

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

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService{

    final String[] MAPKEY = {"body","items","item"};
    final String[] AREAKEY = {"1156000000", //서울시
                                "4136000000", //경기도-남양주시
                                "4711100000", //경상북도-포항시
                                "4822000000", //경상남도-통영시
                                "5011000000", //제주도-제주시
                                "4413100000", //충청남도-천안시
                                "4313000000", //충청북도-충주시
                                "4513000000", //전라북도-군산시
                                "4613000000", //전라남도-여수시
                                "4215000000"}; //강원도-강릉시

    private final WeatherRepository weatherRepository;

    @Scheduled(fixedDelay = 86400 * 1000L)
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
        String DAY = "5";


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
        for(int i=0; i<this.MAPKEY.length; i++){
            if(this.MAPKEY[i].equals("item")){
                items = (List<LinkedTreeMap>) treeMap.get(this.MAPKEY[i]);
            }else{
                treeMap = (LinkedTreeMap) treeMap.get(this.MAPKEY[i]);
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

        weatherResultContext.setWeatherRegion((String) items.get(0).get("doName"));

        return weatherResultContext;
    }
}
