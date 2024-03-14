package com.swd391.backend.service.Interface;

import com.swd391.backend.youtubeapi.VideoInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IYoutubeService {
    ResponseEntity<?> getPlaylistVideos(String playlistUrl);
}
