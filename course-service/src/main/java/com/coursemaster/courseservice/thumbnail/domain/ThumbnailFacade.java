package com.coursemaster.courseservice.thumbnail.domain;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ThumbnailFacade {

    private final ThumbnailService thumbnailService;

    public ThumbnailDTO saveThumbnail(ThumbnailDTO thumbnailDTO) { return thumbnailService.saveThumbnail(thumbnailDTO); }

    public ThumbnailDTO findById(Integer id) { return thumbnailService.findById(id); }

    public Boolean deleteById(Integer id) { return thumbnailService.delete(id); }
}
