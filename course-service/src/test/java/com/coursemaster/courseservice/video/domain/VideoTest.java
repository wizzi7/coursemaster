package com.coursemaster.courseservice.video.domain;

import com.coursemaster.courseservice.BaseTest;
import com.coursemaster.courseservice.authClient.AuthClient;
import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import com.coursemaster.courseservice.thumbnail.domain.ThumbnailFacade;
import com.coursemaster.courseservice.video.api.VideoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VideoTest extends BaseTest {

    @Autowired
    private VideoFacade videoFacade;

    @MockBean
    private AuthClient authClient;

    @MockBean
    private ThumbnailFacade thumbnailFacade;

    @Test
    void getAllInstructorVideos() {
        // given
        // video ejbs
        VideoHelper.prepareVideoEJB(entityManager, 1, 1, "title1", null);
        VideoHelper.prepareVideoEJB(entityManager, 2, 1, "title2", null);
        VideoHelper.prepareVideoEJB(entityManager, 3, 1, "title3", null);
        VideoHelper.prepareVideoEJB(entityManager, 4, 2, "title4", null);
        VideoHelper.prepareVideoEJB(entityManager, 5, 2, "title5", null);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getCredentials()).thenReturn("");
        doReturn(1).when(authClient).getUserIdByToken(any());
        doReturn(ThumbnailDTO.of(1)).when(thumbnailFacade).findById(any());

        // when
        // get all instructor video
        Page<VideoDTO> videos = videoFacade.getAllInstructorVideos(PageRequest.of(0,10));

        // then
        // should return Page result
        assertNotNull(videos);
        assertEquals(3, videos.getTotalElements());
    }

    @Test
    void getVideoDetails() {
        // given
        // video ejbs
        String uuid = UUID.randomUUID().toString();
        VideoHelper.prepareVideoEJB(entityManager, 1, 1, "title1", uuid);
        VideoHelper.prepareVideoEJB(entityManager, 2, 1, "title2", null);
        VideoHelper.prepareVideoEJB(entityManager, 3, 1, "title3", null);
        VideoHelper.prepareVideoEJB(entityManager, 4, 2, "title4", null);
        VideoHelper.prepareVideoEJB(entityManager, 5, 2, "title5", null);
        doReturn("user").when(authClient).getFullUserNameById(any());

        // when
        // get all instructor video
        VideoDTO video = videoFacade.getVideoDetails(uuid);

        // then
        // should return Page result
        assertNotNull(video);
        assertEquals("title1", video.getTitle());
    }

    @Test
    void getNewestVideos() {
        // given
        // video ejbs
        VideoHelper.prepareVideoEJB(entityManager, 1, 1, "title1", null);
        VideoHelper.prepareVideoEJB(entityManager, 2, 1, "title2", null);
        VideoHelper.prepareVideoEJB(entityManager, 3, 1, "title3", null);
        VideoHelper.prepareVideoEJB(entityManager, 4, 2, "title4", null);
        VideoHelper.prepareVideoEJB(entityManager, 5, 2, "title5", null);
        doReturn("user").when(authClient).getFullUserNameById(any());

        // when
        // get all instructor video
        Page<VideoDTO> videos = videoFacade.getNewestVideos(PageRequest.of(0,10));

        // then
        // should return Page result
        assertNotNull(videos);
        assertEquals(5, videos.getTotalElements());
        assertEquals("title5", videos.getContent().get(0).getTitle());
    }
}
