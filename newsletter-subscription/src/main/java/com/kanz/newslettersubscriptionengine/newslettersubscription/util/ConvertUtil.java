package com.kanz.newslettersubscriptionengine.newslettersubscription.util;

import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.SubscribeRequestDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.SubscriberDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;


public class ConvertUtil {
    public static UserEntity convert(SubscribeRequestDto subscribeRequestDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(subscribeRequestDto.getName());
        userEntity.setLastName(subscribeRequestDto.getLastName());
        userEntity.setEmail(subscribeRequestDto.getEmail());
        return userEntity;
    }

    public static SubscriberDto convert(UserEntity userEntity) {
        SubscriberDto subscriberDto = new SubscriberDto();
        subscriberDto.setName(userEntity.getName());
        subscriberDto.setLastName(userEntity.getLastName());
        subscriberDto.setEmail(userEntity.getEmail());
        return subscriberDto;
    }

    public static List<SubscriberDto> convert(List<UserEntity> userEntities) {
        return userEntities.stream().map(ConvertUtil::convert).collect(Collectors.toList());
    }
}
