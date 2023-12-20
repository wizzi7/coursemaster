package com.coursemaster.courseservice.video.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UPLOADED_VIDEOS")
class VideoEJB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uploaded_videos_id_seq")
    private Integer id;
    private LocalDateTime uploadDate;
    private Integer userId;
    private String title;
    private String description;
    private String fileName;
    private String url;
    private Integer thumbnailId;
    @Column(name = "video_uuid")
    private String videoUUID;
}
