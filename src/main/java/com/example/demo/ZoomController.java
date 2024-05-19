package com.example.demo.controller;


import com.example.demo.service.ZoomApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoom")
public class ZoomController {

  private final ZoomApiService zoomApiService;

  @Autowired
  public ZoomController(ZoomApiService zoomApiService) {
    this.zoomApiService = zoomApiService;
  }

  @PostMapping("/create-meeting")
  public ResponseEntity<String> createMeeting(@RequestParam String topic, @RequestParam int duration) {
    try {
      return zoomApiService.createMeeting(topic, duration);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body(e.getMessage());
    }
  }

  @GetMapping("/service-check")
  public String serviceCheck() {
    return "This is ping-pong from Zoom API client application";
  }
  @GetMapping("/auth-check")
  public ResponseEntity<String> checkAuth() {
    try {
      String token = zoomApiService.getAuthToken();
      return ResponseEntity.ok("Access Token: " + token);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body(e.getMessage());
    }
  }
}



