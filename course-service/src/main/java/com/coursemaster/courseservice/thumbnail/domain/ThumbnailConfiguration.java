package com.coursemaster.courseservice.thumbnail.domain;

import com.coursemaster.courseservice.video.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

@Configuration
public class ThumbnailConfiguration {

    @Bean
    ThumbnailDtoToEjbConverter thumbnailDtoToEjbConverter() { return new ThumbnailDtoToEjbConverter(); }

    @Bean
    ThumbnailEjbToDtoConverter thumbnailEjbToDtoConverter() { return new ThumbnailEjbToDtoConverter(); }

    @Bean
    ThumbnailFacade thumbnailFacade(ThumbnailService thumbnailService) { return new ThumbnailFacade(thumbnailService); }

    @Bean
    ThumbnailService thumbnailService(ThumbnailRepository thumbnailRepository, ConversionService conversionService) {
        return new ThumbnailServiceImpl(thumbnailRepository, conversionService);
    }
}
