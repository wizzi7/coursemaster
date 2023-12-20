package com.coursemaster.courseservice.video.domain;

import com.coursemaster.courseservice.video.api.VideoDTO;
import org.springframework.core.convert.converter.Converter;
import java.time.LocalDateTime;
import java.util.UUID;

public class VideoDtoToEjbConverter implements Converter<VideoDTO, VideoEJB> {

    @Override
    public VideoEJB convert(VideoDTO videoDTO) {
        return VideoEJB.builder()
                .title(videoDTO.getTitle())
                .description(videoDTO.getDescription())
                .fileName(videoDTO.getFileName())
                .uploadDate(LocalDateTime.now())
                .videoUUID(UUID.randomUUID().toString())
                .build();
    }
}
