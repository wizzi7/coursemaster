package com.coursemaster.courseservice.video.domain;

import com.coursemaster.courseservice.authClient.AuthClient;
import com.coursemaster.courseservice.thumbnail.domain.ThumbnailFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

@Configuration
public class VideoConfiguration {

    @Bean
    VideoDtoToEjbConverter videoDtoToEjbConverter() { return new VideoDtoToEjbConverter(); }

    @Bean
    VideoEjbToVideoDtoConverter videoEjbToDtoConverter() { return new VideoEjbToVideoDtoConverter(); }

    @Bean
    VideoFacade videoFacade(VideoService videoService) { return new VideoFacade(videoService); }

    @Bean
    VideoService videoService(VideoRepository videoRepository, ConversionService conversionService, ThumbnailFacade thumbnailFacade, AuthClient authClient) {
        return new VideoServiceImpl(videoRepository, conversionService, thumbnailFacade, authClient); }
}
