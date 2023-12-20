package com.coursemaster.courseservice.video.domain;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

public class VideoHelper {

    public static VideoEJB prepareVideoEJB(EntityManager entityManager, Integer id, Integer userId, String title, String uuid) {
        VideoEJB videoEJB = new VideoEJB();
        videoEJB.setId(id);
        videoEJB.setTitle(title);
        videoEJB.setFileName("filename" + id);
        videoEJB.setUserId(userId);
        videoEJB.setThumbnailId(id);
        videoEJB.setVideoUUID(uuid);
        videoEJB.setUploadDate(LocalDateTime.now().plusMinutes(id * 1000));
        return entityManager.merge(videoEJB);
    }
}
