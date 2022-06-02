package com.kanz.newslettersubscriptionengine.newslettersubscription.repo;

import com.kanz.newslettersubscriptionengine.newslettersubscription.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    Page<UserEntity> findAllByCreationDateBefore(Date creationDate, Pageable pageable);
    Page<UserEntity> findAllByCreationDateAfter(Date creationDate, Pageable pageable);
    Page<UserEntity> findAllByCreationDateBetween(Date from, Date to, Pageable pageable);
}