package com.coursemaster.courseservice.thumbnail.domain;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import org.springframework.core.convert.converter.Converter;

public class ThumbnailEjbToDtoConverter implements Converter<ThumbnailEJB, ThumbnailDTO> {
    @Override
    public ThumbnailDTO convert(ThumbnailEJB ejb) {
        return ThumbnailDTO.builder()
                .id(ejb.getId())
                .fileName(ejb.getFileName())
                .contentType(ejb.getContentType())
                .build();
    }
}
