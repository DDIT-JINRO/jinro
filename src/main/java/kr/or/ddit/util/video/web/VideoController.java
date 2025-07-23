package kr.or.ddit.util.video.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.gson.*;

@Slf4j
@Controller
public class VideoController {

	@GetMapping("/video")
	public String videoHome(Model model) throws Exception {

		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType,
				"callType=P2P&liveMode=false&maxJoinCount=4&liveMaxJoinCount=100&layoutType=4&sfuIncludeAll=true&passwd=12341234&roomTitle=%EC%A7%84%EB%A1%9C%EC%9D%B4%EC%A6%88%EB%B0%B1");
		Request request = new Request.Builder()
				.url("https://openapi.gooroomee.com/api/v1/room")
				.post(body)
				.addHeader("accept", "application/json")
				.addHeader("content-type", "application/x-www-form-urlencoded")
				.addHeader("X-GRM-AuthToken", "12056163501988613cf51b7b51cdd8140bb172761d02211a8b")
				.build();

		// 💡 응답 처리 및 파싱
		try (Response response = client.newCall(request).execute()) {

			// 응답 본문을 문자열로 읽음
			String responseBody = response.body().string();
			log.info("responseBody : {}", responseBody);

			// 💡 GSON을 이용하여 JSON 파싱
			JsonObject jsonObj = JsonParser.parseString(responseBody).getAsJsonObject();
			JsonObject data = jsonObj.getAsJsonObject("data");
			JsonObject room = data.getAsJsonObject("room");
			String roomId = room.get("roomId").getAsString(); // 안전하게 roomId 추출

			// 모델에 roomId와 원본 JSON을 전달 (JSP에서 활용 가능)
			model.addAttribute("roomId", roomId);
			model.addAttribute("rawJson", responseBody);

		} catch (Exception e) {
			log.error("화상 회의 방 생성 실패", e);
			model.addAttribute("error", "방 생성 중 오류가 발생했습니다.");
		}

		// 💡 "/WEB-INF/views/video/video.jsp"로 이동
		return "video/video";
	}

	@GetMapping("/room/list")
	public String roomList(Model model) {
		// 요청 데이터 생성자
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("https://openapi.gooroomee.com/api/v1/room/list")
				.get()
				.addHeader("accept", "application/json")
				.addHeader("X-GRM-AuthToken", "12056163501988613cf51b7b51cdd8140bb172761d02211a8b")
				.build();

		log.info("request : " + request);

		try (Response response = client.newCall(request).execute()) {
			String responseBody = response.body().string();
			JsonObject jsonObj = JsonParser.parseString(responseBody).getAsJsonObject();
			JsonArray roomJsonArray = jsonObj.getAsJsonObject("data").getAsJsonArray("list");

			List<Map<String, Object>> roomList = new ArrayList<>();
			Gson gson = new Gson();

			for (JsonElement elem : roomJsonArray) {
				Map<String, Object> roomMap = gson.fromJson(elem, Map.class);
				roomList.add(roomMap);
			}

			model.addAttribute("roomList", roomList);

		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("error", "방 목록 조회 중 오류 발생");
		}

		model.addAttribute("request", request);

		return "video/videoList";
	}

	@PostMapping("/room/enter")
	public String roomEnter(@RequestParam String roomId) {
		log.info("roomId : {}", roomId);

		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

		String payload1 = "roomId=" + roomId + 
						"&username=테스터" + 
						"&roleId=participant" + 
						"&apiUserId=gooroomee-tester" +
						"&ignorePasswd=false";

		String payload2 = "roomId=" + roomId + 
						"&username=마스터" + 
						"&roleId=speaker" + 
						"&apiUserId=gooroomee-master" +
						"&ignorePasswd=false";

		RequestBody body1 = RequestBody.create(mediaType, payload1);
		RequestBody body2 = RequestBody.create(mediaType, payload2);
		String data1 = body1.toString();
		String data2 = body2.toString();
		log.info("body(참가자) : " + data1);
		log.info("body(상담사) : " + data2);

		Request request = new Request.Builder()
				.url("https://openapi.gooroomee.com/api/v1/room/user/otp/url")
				.post(body1)
				.addHeader("accept", "application/json")
				.addHeader("content-type", "application/x-www-form-urlencoded")
				.addHeader("X-GRM-AuthToken", "12056163501988613cf51b7b51cdd8140bb172761d02211a8b")
				.build();
		log.info("requst : " + request);

		Request request2 = new Request.Builder()
				.url("https://openapi.gooroomee.com/api/v1/room/user/otp/url")
				.post(body2)
				.addHeader("accept", "application/json")
				.addHeader("content-type", "application/x-www-form-urlencoded")
				.addHeader("X-GRM-AuthToken", "12056163501988613cf51b7b51cdd8140bb172761d02211a8b")
				.build();
		log.info("requst : " + request2);

		try {

			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();
			log.info("room/enter(참가자) : " + responseBody);

			response = client.newCall(request2).execute();
			responseBody = response.body().string();
			log.info("room/enter(상담사) : " + responseBody);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "video/enter";
	}

}
