package com.coursemaster.courseservice.thumbnail.domain;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.StringUtils;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
@RequiredArgsConstructor
class ThumbnailServiceImpl implements ThumbnailService {
    private final String SAVE_DIR_PATH = "C:\\Users\\Pc\\Desktop\\files\\thumbnails";

    private final ThumbnailRepository thumbnailRepository;

    private final ConversionService conversionService;

    @Override
    public ThumbnailDTO saveThumbnail(ThumbnailDTO thumbnailDTO) {
        log.info("Start saving thumbnail with filename: {}", thumbnailDTO.getFileName());
        try {
            ThumbnailEJB thumbnailEJB = conversionService.convert(thumbnailDTO, ThumbnailEJB.class);
            thumbnailEJB = thumbnailRepository.save(thumbnailEJB);

            thumbnailEJB.setUrl(saveFileOnDisc(thumbnailEJB, thumbnailDTO.getData()).toString());
            thumbnailRepository.save(thumbnailEJB);
            log.info("Finish saving thumbnail with filename: {}", thumbnailDTO.getFileName());
            return conversionService.convert(thumbnailEJB, ThumbnailDTO.class);
        } catch (IOException e) {
            log.warn("Thumbnail {} cannot be saved properly", thumbnailDTO.getFileName());
            return null;
        }
    }

    private Path saveFileOnDisc(ThumbnailEJB thumbnailEJB, byte[] data) throws IOException {
        return Files.write(Paths.get(SAVE_DIR_PATH).resolve(thumbnailEJB.getFileName()), data, StandardOpenOption.CREATE);
    }

    private ThumbnailDTO convertToDtoWithData(ThumbnailEJB thumbnailEJB) {
        if (StringUtils.hasText(thumbnailEJB.getUrl())) {
            try {
                ThumbnailDTO thumbnailDTO = conversionService.convert(thumbnailEJB, ThumbnailDTO.class);
                return thumbnailDTO.withData(getThumbnailFromDisk(thumbnailEJB.getUrl()));
            } catch (Exception e) {
                log.warn("Cannot get thumbnail with id: {}", thumbnailEJB.getId());
                return null;
            }
        }
        log.warn("Thumbnail with id: {} has no url in database", thumbnailEJB.getId());
        return null;
    }

    private byte[] getThumbnailFromDisk(String url) throws IOException {
        File file = new File(url);
        if (file.exists())
            return FileUtils.readFileToByteArray(file);
        return null;
    }

    public ThumbnailDTO findById(Integer id) {
        return thumbnailRepository.findById(id).map(this::convertToDtoWithData).orElse(null);
    }

    @Override
    public Boolean delete(Integer id) {
        return this.thumbnailRepository.findById(id).map(this::deleteThumbnailFromDisc).orElse(false);
    }

    private Boolean deleteThumbnailFromDisc(ThumbnailEJB thumbnailEJB) {
        if (new File(thumbnailEJB.getUrl()).delete()) {
            thumbnailRepository.delete(thumbnailEJB);
            log.info("Thumbnail with id {} has been deleted from disc", thumbnailEJB.getId());
            return true;
        } else return false;
    }
}
