package com.coursemaster.courseservice.video.domain;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import com.coursemaster.courseservice.video.api.VideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.File;

@RequiredArgsConstructor
public class VideoFacade {

    private final VideoService videoService;

    public VideoDTO saveVideo(VideoDTO videoDTO, ThumbnailDTO thumbnailDTO) { return videoService.saveVideo(videoDTO, thumbnailDTO); }

    public Page<VideoDTO> getAllInstructorVideos(Pageable pageable) { return videoService.getAllInstructorVideos(pageable); }

    public Boolean deleteById(Integer id) { return videoService.delete(id); }

    public Page<VideoDTO> getAllVideosByTitle(String searchCriteria, Pageable pageable) { return videoService.getAllVideosByTitle(searchCriteria, pageable); }

    public VideoDTO getVideoDetails(String videoUUID) { return videoService.getVideoDetails(videoUUID); }

    public File videoPlayback(String videoUUID) { return videoService.videoPlayback(videoUUID); }

    public Page<VideoDTO> getNewestVideos(Pageable pageable) { return videoService.getNewestVideos(pageable); }
}
