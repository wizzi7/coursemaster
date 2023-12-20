package com.coursemaster.courseservice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@Rollback
@Transactional
@ActiveProfiles(profiles = {"test"})
public class BaseTest {

    @PersistenceContext
    protected EntityManager entityManager;
}
