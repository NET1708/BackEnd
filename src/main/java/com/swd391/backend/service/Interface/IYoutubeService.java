package com.swd391.backend.service.Interface;

import com.swd391.backend.youtubeapi.VideoInfo;

import java.util.List;

public interface IYoutubeService {
    List<VideoInfo> getPlaylistVideos(String playlistUrl);
}
