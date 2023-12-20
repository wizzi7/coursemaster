package com.coursemaster.courseservice.thumbnail.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ThumbnailRepository extends CrudRepository<ThumbnailEJB, Integer> {


}
