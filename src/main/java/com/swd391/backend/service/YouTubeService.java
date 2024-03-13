package com.swd391.backend.service;

import com.swd391.backend.dao.ChapterRepository;
import com.swd391.backend.dao.CourseRepository;
import com.swd391.backend.entity.Chapter;
import com.swd391.backend.entity.Course;
import com.swd391.backend.youtubeapi.Item;
import com.swd391.backend.youtubeapi.VideoInfo;
import com.swd391.backend.youtubeapi.YouTubeResponse;
import com.swd391.backend.service.Interface.IYoutubeService;
import jakarta.mail.FetchProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class YouTubeService implements IYoutubeService {
    private static final String API_KEY = "AIzaSyBysuQgh_I1gCSxukDQ_iv24jsea35Vmnk";
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public ResponseEntity<?> getPlaylistVideos(String playlistUrl) {
        String url = "https://www.youtube.com/playlist?list=" + playlistUrl;
        RestTemplate restTemplate = new RestTemplate();
        String nextPageToken = null;
        List<VideoInfo> videoInfos = new ArrayList<>();
        try {
            do {
                String apiUrl = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=" + playlistUrl + "&key=" + API_KEY;
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
            List<Chapter> chapters = new ArrayList<>();
            for (VideoInfo videoInfo : videoInfos) {
                Course course = courseRepository.findTopByOrderByCourseIdDesc();
                if (course != null) {
                    Chapter chapter = new Chapter(); // Tạo mới một đối tượng Chapter
                    chapter.setCourse(course);
                    chapter.setChapterContent(videoInfo.getUrl());
                    chapter.setChapterName(videoInfo.getTitle());
                    chapters.add(chapter);
                } else {
                    return ResponseEntity.badRequest().body("No course found");
                }
            }
            chapterRepository.saveAll(chapters);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid playlist URL");
        }
        return ResponseEntity.ok(videoInfos);
    }
}
