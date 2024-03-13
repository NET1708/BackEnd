package com.swd391.backend.service;

import com.swd391.backend.youtubeapi.Item;
import com.swd391.backend.youtubeapi.VideoInfo;
import com.swd391.backend.youtubeapi.YouTubeResponse;
import com.swd391.backend.service.Interface.IYoutubeService;
import jakarta.mail.FetchProfile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class YouTubeService implements IYoutubeService {
    private static final String API_KEY = "AIzaSyBysuQgh_I1gCSxukDQ_iv24jsea35Vmnk";


    @Override
    public List<VideoInfo> getPlaylistVideos(String playlistUrl) {

        String playlistId = extractPlaylistId(playlistUrl);
        RestTemplate restTemplate = new RestTemplate();
        String nextPageToken = null;
        List<VideoInfo> videoInfos = new ArrayList<>();

        do {
            String apiUrl = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=" + playlistId + "&key=" + API_KEY;
            if (nextPageToken != null) {
                apiUrl += "&pageToken=" + nextPageToken;
            }

            YouTubeResponse response = restTemplate.getForObject(apiUrl, YouTubeResponse.class);

            if (response != null && response.getItems() != null) {
                for (Item item : response.getItems()) {
                    String videoTitle = item.getSnippet().getTitle();
                    String videoId = item.getSnippet().getResourceId().getVideoId();
                    String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
                    videoInfos.add(new VideoInfo(videoTitle, videoUrl));
                }
            }

            nextPageToken = response.getNextPageToken();
        } while (nextPageToken != null);

        return videoInfos;
    }

    private String extractPlaylistId(String playlistUrl) {
        String playlistId = "";
        if (playlistUrl.contains("list=")) {
            int startIndex = playlistUrl.indexOf("list=") + 5;
            int endIndex = playlistUrl.indexOf("&", startIndex);
            if (endIndex == -1) {
                endIndex = playlistUrl.length();
            }
            playlistId = playlistUrl.substring(startIndex, endIndex);
        }
        return playlistId;
    }
}
