package com.coursemaster.courseservice.thumbnail.api;

import lombok.*;

@Builder
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailDTO {
    private Integer id;
    private String fileName;
    private byte[] data;
    private String url;
    private String contentType;

    public static ThumbnailDTO of(Integer id) {
        return new ThumbnailDTO(id, null, null, null, null);
    }

    public static ThumbnailDTO of(byte[] data, String fileName, String contentType) {
        return new ThumbnailDTO(null, fileName, data, null, contentType);
    }
}
