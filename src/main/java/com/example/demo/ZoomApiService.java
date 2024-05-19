package com.example.demo.service;

import com.example.demo.authentication.ZoomAuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZoomApiService {

  @Autowired
  private ZoomAuthenticationHelper zoomAuthenticationHelper;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${zoom.oauth2.api-url}")
  private String zoomApiUrl;

  private static final String CREATE_MEETING_URL = "%s/users/me/meetings";

  public ResponseEntity<String> createMeeting(String topic, int duration) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(zoomAuthenticationHelper.getAuthenticationToken());

    Map<String, Object> request = new HashMap<>();
    request.put("topic", topic);
    request.put("type", 2); // Scheduled meeting
    request.put("duration", duration);
    request.put("settings", Map.of(
        "host_video", true,
        "participant_video", true
    ));

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

    String url = String.format(CREATE_MEETING_URL, zoomApiUrl);
    return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
  }
  public String getAuthToken() throws Exception {
    return zoomAuthenticationHelper.getAuthenticationToken();
  }

}

