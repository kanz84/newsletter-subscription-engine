package com.kanz.newslettersubscriptionengine.newslettersubscription.business;

import com.kanz.newslettersubscriptionengine.common.entity.BaseEntity;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.FetchSubscribersRequestDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.SubscriberDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.entity.UserEntity;
import com.kanz.newslettersubscriptionengine.newslettersubscription.repo.UserRepository;
import com.kanz.newslettersubscriptionengine.newslettersubscription.util.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

import static com.kanz.newslettersubscriptionengine.common.util.EmptyUtil.isNotNull;
import static com.kanz.newslettersubscriptionengine.common.util.EmptyUtil.isNull;


public class PageableUserFinder {
    private static final Logger logger = LoggerFactory.getLogger(PageableUserFinder.class);

    private UserRepository userRepository;
    private Date from;
    private Date to;
    private Pageable pageable;

    public PageableUserFinder(UserRepository userRepository, FetchSubscribersRequestDto requestDto, int pageSize) {
        this.userRepository = userRepository;
        this.from = requestDto.getFrom();
        this.to = requestDto.getTo();
        this.pageable = PageRequest.of(requestDto.getPage(), pageSize, Sort.by(BaseEntity.COLUMN_NAME_ID));
    }

    public List<SubscriberDto> find() {
        return ConvertUtil.convert(findPage().getContent());
    }

    private Page<UserEntity> findPage() {
        if (shouldFindBetween()) return findAllByCreationDateBetween();
        if (shouldFindBefore()) return findAllByCreationDateBefore();
        if (shouldFindAfter()) return findAllByCreationDateAfter();
        return findAll();
    }

    private Page<UserEntity> findAllByCreationDateBetween() {
        logger.info("findAll users between ({}) and ({}).", from, to);
        return userRepository.findAllByCreationDateBetween(from, to, pageable);
    }

    private Page<UserEntity> findAllByCreationDateBefore() {
        logger.info("findAll users before ({}).", to);
        return userRepository.findAllByCreationDateBefore(to, pageable);
    }

    private Page<UserEntity> findAllByCreationDateAfter() {
        logger.info("findAll users after ({}).", from);
        return userRepository.findAllByCreationDateAfter(from, pageable);
    }

    private Page<UserEntity> findAll() {
        logger.info("findAll users.");
        return userRepository.findAll(pageable);
    }

    private boolean shouldFindBetween() {
        return isNotNull(from) && isNotNull(to);
    }

    private boolean shouldFindBefore() {
        return isNull(from) && isNotNull(to);
    }

    private boolean shouldFindAfter() {
        return isNotNull(from) && isNull(to);
    }

}
