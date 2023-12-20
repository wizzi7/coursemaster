package com.coursemaster.courseservice.thumbnail.domain;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;

import java.util.Optional;

interface ThumbnailService {

    ThumbnailDTO saveThumbnail(ThumbnailDTO thumbnailDTO);

    ThumbnailDTO findById(Integer id);

    Boolean delete(Integer id);
}
