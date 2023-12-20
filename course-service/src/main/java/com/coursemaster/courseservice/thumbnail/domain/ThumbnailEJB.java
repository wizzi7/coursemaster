package com.coursemaster.courseservice.thumbnail.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UPLOADED_THUMBNAILS")
class ThumbnailEJB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uploaded_thumbnails_id_seq")
    private Integer id;
    private String fileName;
    private String url;
    private String contentType;
}
