package com.coursemaster.courseservice.video.web;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import com.coursemaster.courseservice.video.api.VideoDTO;
import com.coursemaster.courseservice.video.domain.VideoFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@EnableMethodSecurity
@RequestMapping("/api/v1/courses/videos")
public class VideoController {

    private final VideoFacade videoFacade;

    @Operation(summary = "Saves multipart file", description = "Saves multipart file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saved file"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('ADD_VIDEO')")
    @PostMapping("/save")
    public ResponseEntity<VideoDTO> saveFile(@RequestParam("title") String title, @RequestParam("description") String description,
                                             @RequestParam("video") MultipartFile video, @RequestParam("thumbnail") MultipartFile thumbnail) throws IOException {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(description) || video == null || thumbnail == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(videoFacade.saveVideo(VideoDTO.of(video.getBytes(), video.getOriginalFilename(), title, description),
                ThumbnailDTO.of(thumbnail.getBytes(), thumbnail.getOriginalFilename(), thumbnail.getContentType())));// do poprawy albo testu
    }

    @Operation(summary = "Deletes video and thumbnail", description = "Deletes video and thumbnail by video id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('DELETE_VIDEO')")
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id) {
        return Optional.ofNullable(videoFacade.deleteById(id))
                .map(result -> ResponseEntity.ok().body(result))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Records for list of videos", description = "Records for list of videos owned by instructor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of videos"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('READ_VIDEO_LIST')")
    @GetMapping("/getAllInstructorVideos")
    public ResponseEntity<Page<VideoDTO>> getAllInstructorVideos(Pageable pageable) {
        return Optional.ofNullable(videoFacade.getAllInstructorVideos(pageable))
                    .map(result -> ResponseEntity.ok().body(result))
                    .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Records for list of videos searched by criteria", description = "Records for list of videos searched by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of videos"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/getAllVideosByTitle")
    public ResponseEntity<Page<VideoDTO>> getAllVideosByTitle(@RequestParam("searchCriteria") String searchCriteria, Pageable pageable) {
        return Optional.ofNullable(videoFacade.getAllVideosByTitle(searchCriteria, pageable))
                .map(result -> ResponseEntity.ok().body(result))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Records for list of videos", description = "Records for list of videos ordered by upload date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of videos"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/getNewestVideos")
    public ResponseEntity<Page<VideoDTO>> getNewestVideos(Pageable pageable) {
        return Optional.ofNullable(videoFacade.getNewestVideos(pageable))
                .map(result -> ResponseEntity.ok().body(result))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Returns video details", description = "Returns video details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Video details"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/getVideoDetails/{videoUUID}")
    public ResponseEntity<VideoDTO> getVideoDetails(@PathVariable("videoUUID") String videoUUID) {
        return Optional.ofNullable(videoFacade.getVideoDetails(videoUUID))
                .map(result -> ResponseEntity.ok().body(result))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Returns partial video", description = "Returns partial video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partial video"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("videoPlayback/{videoUUID}")
    public ResponseEntity<InputStreamResource> videoPlayback(@PathVariable String videoUUID, HttpServletRequest request) throws FileNotFoundException {
        File videoFile = videoFacade.videoPlayback(videoUUID);
        if (videoFile == null)
            return ResponseEntity.badRequest().build();

        long fileSize = videoFile.length();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("video/mp4"));

        if (request.getHeader("Range") != null) {
            String rangeHeader = request.getHeader("Range");
            long rangeStart = Long.parseLong(rangeHeader.split("=")[1].split("-")[0].trim());
            long rangeEnd;
            if (rangeHeader.split("=")[1].split("-").length > 1)
                rangeEnd = Long.parseLong(rangeHeader.split("=")[1].split("-")[1].trim());
            else
                rangeEnd = fileSize - 1;

            headers.add("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileSize);

            InputStreamResource resource = new InputStreamResource(new FileInputStream(videoFile));
            return new ResponseEntity<>(resource, headers, HttpStatus.PARTIAL_CONTENT);
        } else {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(videoFile));
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
    }
}
