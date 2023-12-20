package com.coursemaster.courseservice.video.domain;

import com.coursemaster.courseservice.authClient.AuthClient;
import com.coursemaster.courseservice.thumbnail.domain.ThumbnailFacade;
import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import com.coursemaster.courseservice.video.api.VideoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import com.coursemaster.courseservice.user.api.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
class VideoServiceImpl implements VideoService {
    private final String SAVE_DIR_PATH = "C:\\Users\\Pc\\Desktop\\files\\videos";

    private final VideoRepository videoRepository;

    private final ConversionService conversionService;

    private final ThumbnailFacade thumbnailFacade;

    private final AuthClient authClient;

    @Override
    public VideoDTO saveVideo(VideoDTO videoDTO, ThumbnailDTO thumbnailDTO) {
        log.info("Start saving video with filename: {}", videoDTO.getFileName());
        ThumbnailDTO savedThumbnailDTO = thumbnailFacade.saveThumbnail(thumbnailDTO);
        if (savedThumbnailDTO != null) {
            VideoEJB videoEJB = conversionService.convert(videoDTO, VideoEJB.class);
            videoEJB = videoRepository.save(videoEJB);

            try {
                videoEJB.setUrl(saveFileOnDisc(videoEJB, videoDTO.getData()).toString());
            } catch (IOException e) {
                log.warn("Video {} cannot be saved properly", videoDTO.getFileName());
                return null;
            }

            videoEJB.setUserId(authClient.getUserIdByToken(CurrentUser.getCurrentUserToken().orElse(null)));
            videoEJB.setThumbnailId(savedThumbnailDTO.getId());
            videoRepository.save(videoEJB);
            VideoDTO savedVideoDTO = conversionService.convert(videoEJB, VideoDTO.class);
            savedVideoDTO.setThumbnailDTO(savedThumbnailDTO);
            log.info("Finish saving video with filename: {}", videoDTO.getFileName());
            return savedVideoDTO;
        }
        log.warn("Video {} cannot be saved properly", videoDTO.getFileName());
        return null;
    }

    private Path saveFileOnDisc(VideoEJB videoEJB, byte[] data) throws IOException {
        return Files.write(Paths.get(SAVE_DIR_PATH).resolve(videoEJB.getFileName()), data, StandardOpenOption.CREATE);
    }

    @Override
    public Page<VideoDTO> getAllInstructorVideos(Pageable pageable) {
        Integer userId = authClient.getUserIdByToken(CurrentUser.getCurrentUserToken().orElse(null));
        return videoRepository.getAllInstructorVideos(userId, pageable)
                .map(videoEJB -> {
                    Integer thumbnailId = videoEJB.getThumbnailId();
                    VideoDTO videoDTO = Objects.requireNonNull(conversionService.convert(videoEJB, VideoDTO.class));
                    videoDTO.setThumbnailDTO(thumbnailFacade.findById(thumbnailId));
                    return videoDTO;
                });
    }

    @Override
    public Boolean delete(Integer id) {
        return videoRepository.findById(id).map(videoEJB -> {
            if (thumbnailFacade.deleteById(videoEJB.getThumbnailId())) {
                log.info("Video with id {} has been deleted from database", videoEJB.getId());
                return deleteVideoFromDisc(videoEJB);
            }
            else return false;
        }).orElse(false);
    }

    private Boolean deleteVideoFromDisc(VideoEJB videoEJB) {
        if (new File(videoEJB.getUrl()).delete()) {
            videoRepository.delete(videoEJB);
            log.info("Video with id {} has been deleted from disc", videoEJB.getId());
            return true;
        } else return false;
    }

    @Override
    public Page<VideoDTO> getAllVideosByTitle(String searchCriteria, Pageable pageable) {
        List<VideoDTO> videos = videoRepository.searchWithFullText(searchCriteria, pageable).stream()
                .map(videoEJB -> conversionService.convert(videoEJB, VideoDTO.class))
                .filter(Objects::nonNull)
                .map(videoDTO -> videoDTO.withCreatorFullName(authClient.getFullUserNameById(videoDTO.getUserId())))
                .toList();

        Integer totalElements = videoRepository.countFullTextSearchResults(searchCriteria);

        return new PageImpl<>(videos, pageable, totalElements);
    }

    @Override
    public VideoDTO getVideoDetails(String videoUUID) {
        return videoRepository.findFirstByVideoUUID(videoUUID)
                .map(videoEJB -> conversionService.convert(videoEJB, VideoDTO.class))
                .map(videoDTO -> videoDTO.withCreatorFullName(authClient.getFullUserNameById(videoDTO.getUserId())))
                .orElse(null);
    }

    @Override
    public File videoPlayback(String videoUUID) {
        String videoLocation = videoRepository.findFirstByVideoUUID(videoUUID).map(VideoEJB::getUrl).orElse(null);
        if (StringUtils.hasText(videoLocation)) {
            Path videoPath = Paths.get(videoLocation);
            File videoFile = videoPath.toFile();
            if (!videoFile.exists() || !videoFile.isFile()) {
                log.info("Cannot find video with uuid: {}", videoUUID);
            }
            return videoFile;
        }
        return null;
    }

    @Override
    public Page<VideoDTO> getNewestVideos(Pageable pageable) {
        return videoRepository.getNewestVideos(pageable)
                .map(videoEJB -> conversionService.convert(videoEJB, VideoDTO.class))
                .map(videoDTO -> videoDTO.withCreatorFullName(authClient.getFullUserNameById(videoDTO.getUserId())));
    }
}
