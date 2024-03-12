package com.swd391.backend.youtubeapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoInfo {
    private String title;
    private String url;

    // Getters and setters
}
