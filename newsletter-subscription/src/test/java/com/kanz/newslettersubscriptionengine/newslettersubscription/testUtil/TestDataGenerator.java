package com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil;

import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.FetchSubscribersRequestDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.SubscribeRequestDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.UnsubscribeRequestDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.entity.UserEntity;
import com.kanz.newslettersubscriptionengine.newslettersubscription.repo.UserRepository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class TestDataGenerator {

    public static final String TEST_USER_NAME = "Steve";
    public static final String TEST_USER_LAST_NAME = "Jobs";
    public static final String TEST_USER_EMAIL = "steve.jobs@gmail.com";

    public static String getTestUserEmail(){
        return TEST_USER_EMAIL;
    }

    public static SubscribeRequestDto createSubscribeRequestDto(){
        SubscribeRequestDto subscribeRequestDto = new SubscribeRequestDto();
        subscribeRequestDto.setName(TEST_USER_NAME);
        subscribeRequestDto.setLastName(TEST_USER_LAST_NAME);
        subscribeRequestDto.setEmail(getTestUserEmail());
        return subscribeRequestDto;
    }

    public static UnsubscribeRequestDto createUnsubscribeRequestDto(){
        UnsubscribeRequestDto unsubscribeRequestDto = new UnsubscribeRequestDto();
        unsubscribeRequestDto.setEmail(getTestUserEmail());
        return unsubscribeRequestDto;
    }

    public static UserEntity createUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(TEST_USER_NAME);
        userEntity.setLastName(TEST_USER_LAST_NAME);
        userEntity.setEmail(getTestUserEmail());
        return userEntity;
    }

    public static List<UserEntity> insertMany(UserRepository userRepository, int count) {
        List<UserEntity>users = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            UserEntity userEntity = TestDataGenerator.createUserEntity();
            userEntity.setEmail(String.format("steve.jobs.%05d@gmail.com", i));
            users.add(userRepository.save(userEntity));
        }
        return users;
    }

    public static FetchSubscribersRequestDto createFetchSubscribersRequestDto(Date from, Date to, int page) {
        FetchSubscribersRequestDto fetchSubscribersReq = new FetchSubscribersRequestDto();
        fetchSubscribersReq.setFrom(from);
        fetchSubscribersReq.setTo(to);
        fetchSubscribersReq.setPage(page);
        return fetchSubscribersReq;
    }

}
