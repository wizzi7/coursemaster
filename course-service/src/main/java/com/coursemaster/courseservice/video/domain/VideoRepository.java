package com.coursemaster.courseservice.video.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
interface VideoRepository extends CrudRepository<VideoEJB, Integer> {

    @Query(value = "select u from VideoEJB u where u.userId = :userId")
    Page<VideoEJB> getAllInstructorVideos(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM uploaded_videos WHERE title_vector @@ plainto_tsquery('english', :searchCriteria) " +
            "ORDER BY ts_rank(title_vector, plainto_tsquery('english', :searchCriteria)) DESC ", nativeQuery = true)
    List<VideoEJB> searchWithFullText(String searchCriteria, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM uploaded_videos " +
            "WHERE title_vector @@ plainto_tsquery('english', ?1)", nativeQuery = true)
    Integer countFullTextSearchResults(String searchCriteria);

    Optional<VideoEJB> findFirstByVideoUUID(String videoUUID);

    @Query(value = "select u from VideoEJB u order by u.uploadDate desc")
    Page<VideoEJB> getNewestVideos(Pageable pageable);
}
