package kr.or.ddit.empt.enp.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class KakaoGeocodingAPI {
    private static final String API_KEY = "1566bb1bb9b3fa84a27451e43ba83182";  // 카카오 API 키

    // 카카오 역지오코딩 호출
    public static String getAddress(double latitude, double longitude) {
        try {
            String urlString = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=" + longitude + "&y=" + latitude;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "KakaoAK " + API_KEY);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            // JSON 응답을 파싱
            JSONObject jsonResponse = new JSONObject(response.toString());
            String address = jsonResponse.getJSONArray("documents")
                                        .getJSONObject(0)
                                        .getJSONObject("address")
                                        .getString("address_name");
            return address;  // 반환되는 주소
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
