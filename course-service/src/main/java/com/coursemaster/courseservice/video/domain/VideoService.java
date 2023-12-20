package com.coursemaster.courseservice.video.domain;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import com.coursemaster.courseservice.video.api.VideoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;

interface VideoService {

    VideoDTO saveVideo(VideoDTO videoDTO, ThumbnailDTO thumbnailDTO);

    Page<VideoDTO> getAllInstructorVideos(Pageable pageable);

    Boolean delete(Integer id);

    Page<VideoDTO> getAllVideosByTitle(String searchCriteria, Pageable pageable);

    VideoDTO getVideoDetails(String videoUUID);

    File videoPlayback(String videoUUID);

    Page<VideoDTO> getNewestVideos(Pageable pageable);
}
