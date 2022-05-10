package com.example.tlover.global.weather.service;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.global.weather.Secret.WeatherSecretKey;
import com.example.tlover.global.weather.dto.WeatherResultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.luxsuen.jsonutil.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService{

    final String[] MAPKEY = {"body","items","item"};


    @SneakyThrows
    public void getWeather(){


//        Timestamp now = new Timestamp(System.currentTimeMillis());
//        SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMdd");
//        String nowSt = s
//        df.format(now);

        String apiUrl = "http://apis.data.go.kr/1360000/TourStnInfoService/getTourStnVilageFcst";

        String serviceKey = WeatherSecretKey.WEATHER_SERVICE_KEY;

        String pageNo = "1";	//페이지 번호
        String numOfRows = "5";	//한 페이지 결과 수
        String dataType = "JSON";	//데이터 타입
        String CURRENT_DATE = "20220510";	//조회하고싶은 날짜
        String HOUR = "24";	//조회하고 싶은 날짜의 시간 날짜
        String COURSE_ID = "1";	//관광 코스ID


        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("CURRENT_DATE","UTF-8") + "=" + URLEncoder.encode(CURRENT_DATE, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("HOUR","UTF-8") + "=" + URLEncoder.encode(HOUR, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("COURSE_ID","UTF-8") + "=" + URLEncoder.encode(COURSE_ID, "UTF-8"));

        /*
         * GET방식으로 전송해서 파라미터 받아오기
         */
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
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

        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject)parser.parse(result.toString());

        Gson gson = new Gson();
        Map map = new HashMap();
        map = (Map)gson.fromJson(obj, map.getClass());
        LinkedTreeMap treeMap = (LinkedTreeMap) map.get("response");

        List<String> items = new ArrayList<>();

        for(int i=0; i<this.MAPKEY.length; i++){
            if(this.MAPKEY[i].equals("item")){
                items = (List<String>) treeMap.get(this.MAPKEY[i]);
            }else{
                treeMap = (LinkedTreeMap) treeMap.get(this.MAPKEY[i]);
            }
        }

    }
}
