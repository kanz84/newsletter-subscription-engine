package com.kanz.newslettersubscriptionengine.newslettersubscription.service.impl;

import com.kanz.newslettersubscriptionengine.common.entity.MyResponseEntity;
import com.kanz.newslettersubscriptionengine.common.service.BaseService;
import com.kanz.newslettersubscriptionengine.newslettersubscription.business.PageableUserFinder;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.*;
import com.kanz.newslettersubscriptionengine.newslettersubscription.entity.UserEntity;
import com.kanz.newslettersubscriptionengine.newslettersubscription.repo.UserRepository;
import com.kanz.newslettersubscriptionengine.newslettersubscription.service.UserService;
import com.kanz.newslettersubscriptionengine.newslettersubscription.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.kanz.newslettersubscriptionengine.common.factory.Factory.createResponse;


@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    UserRepository userRepository;
    @Value("${list.users.page.size}")
    private int pageSize;

    @Transactional
    @Override
    public MyResponseEntity<SubscribeResponseDto> createUser(SubscribeRequestDto subscribeRequestDto) {
        UserEntity userEntity = ConvertUtil.convert(subscribeRequestDto);
        logger.info("User ({}) is creating...", userEntity);
        if (userRepository.existsByEmail(subscribeRequestDto.getEmail())) {
            return createResponse(createSubscribeResponseDto(subscribeRequestDto), HttpStatus.CONFLICT);
        }
        userRepository.save(userEntity);
        return createResponse(createSubscribeResponseDto(subscribeRequestDto), HttpStatus.CREATED);
    }

    private SubscribeResponseDto createSubscribeResponseDto(SubscribeRequestDto subscribeRequestDto) {
        SubscribeResponseDto subscribeResponseDto = new SubscribeResponseDto();
        subscribeResponseDto.setUsername(subscribeRequestDto.getEmail());
        return subscribeResponseDto;
    }

    @Override
    @Transactional
    public MyResponseEntity<UnsubscribeResponseDto> deleteUser(UnsubscribeRequestDto unsubscribeRequestDto) {
        userRepository.deleteByEmail(unsubscribeRequestDto.getEmail());
        return createResponse(new UnsubscribeResponseDto(), HttpStatus.OK);
    }

    @Override
    public MyResponseEntity<IsSubscribedResponseDto> existUser(String email) {
        IsSubscribedResponseDto isSubscribedResponseDto = new IsSubscribedResponseDto();
        isSubscribedResponseDto.setStatus(userRepository.existsByEmail(email));
        return createResponse(isSubscribedResponseDto, HttpStatus.OK);
    }

    @Override
    public MyResponseEntity<FetchSubscribersResponseDto> findUsers(FetchSubscribersRequestDto fetchSubscribersReq) {
        FetchSubscribersResponseDto fetchSubscribersResponseDto = new FetchSubscribersResponseDto();
        PageableUserFinder pageableUserFinder = new PageableUserFinder(userRepository, fetchSubscribersReq, pageSize);
        fetchSubscribersResponseDto.setSubscribers(pageableUserFinder.find());
        return createResponse(fetchSubscribersResponseDto, HttpStatus.OK);
    }

}
