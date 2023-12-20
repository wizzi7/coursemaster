package com.coursemaster.courseservice.thumbnail.domain;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import org.springframework.core.convert.converter.Converter;

public class ThumbnailDtoToEjbConverter implements Converter<ThumbnailDTO, ThumbnailEJB> {
    @Override
    public ThumbnailEJB convert(ThumbnailDTO thumbnailDTO) {
        return ThumbnailEJB.builder()
                .fileName(thumbnailDTO.getFileName())
                .contentType(thumbnailDTO.getContentType())
                .build();
    }
}
