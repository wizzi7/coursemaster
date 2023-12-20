package com.coursemaster.courseservice.video.api;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private byte[] data;
    private String fileName;
    private String url;
    private LocalDateTime uploadDate;
    private Integer userId;
    private ThumbnailDTO thumbnailDTO;
    private String creatorFullName;
    private String videoUUID;

    public static VideoDTO of(byte[] data, String fileName, String title, String description) {
        return new VideoDTO(null, title, description, data, fileName, null, null, null, null, null, null);
    }
}
