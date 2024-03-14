package com.swd391.backend.controller;

import com.swd391.backend.service.YouTubeService;
import com.swd391.backend.youtubeapi.VideoInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Playlist management", description = "Playlist management APIs")
public class PlaylistController {
    @Autowired
    private YouTubeService youTubeService;

    @PostMapping("/playlist/videos")
    public ResponseEntity<?> getPlaylistVideos(@RequestBody Map<String, String> requestBody) {
        String playlistUrl = requestBody.get("url");
        youTubeService.getPlaylistVideos(playlistUrl);
        return ResponseEntity.ok("Playlist videos added successfully");
    }
}
