package com.coursemaster.courseservice.video.domain;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import com.coursemaster.courseservice.video.api.VideoDTO;
import org.springframework.core.convert.converter.Converter;

public class VideoEjbToVideoDtoConverter implements Converter<VideoEJB, VideoDTO> {
    @Override
    public VideoDTO convert(VideoEJB ejb) {
        return VideoDTO.builder()
                .id(ejb.getId())
                .title(ejb.getTitle())
                .description(ejb.getDescription())
                .fileName(ejb.getFileName())
                .uploadDate(ejb.getUploadDate())
                .userId(ejb.getUserId())
                .thumbnailDTO(ThumbnailDTO.of(ejb.getThumbnailId()))
                .videoUUID(ejb.getVideoUUID())
                .build();
    }
}
