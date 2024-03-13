package com.swd391.backend.controller;

import com.swd391.backend.service.YouTubeService;
import com.swd391.backend.youtubeapi.VideoInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Playlist management", description = "Playlist management APIs")
public class PlaylistController {
    @Autowired
    private YouTubeService youTubeService;

    @GetMapping("/playlist/videos")
    public List<VideoInfo> getPlaylistVideos(@RequestParam String playlistUrl) {
        return youTubeService.getPlaylistVideos(playlistUrl);
    }
}
