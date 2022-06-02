package com.kanz.newslettersubscriptionengine.newslettersubscription.util;

import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.SubscribeRequestDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.SubscriberDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.entity.UserEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;


public class ConvertUtilTest {
    @Test
    public void convertToUserEntityTest(){
        SubscribeRequestDto subscribeRequestDto = new SubscribeRequestDto();
        subscribeRequestDto.setName("name");
        subscribeRequestDto.setLastName("lastname");
        subscribeRequestDto.setEmail("email");
        UserEntity userEntity = ConvertUtil.convert(subscribeRequestDto);
        Assert.assertEquals(subscribeRequestDto.getName(), userEntity.getName());
        Assert.assertEquals(subscribeRequestDto.getLastName(), userEntity.getLastName());
        Assert.assertEquals(subscribeRequestDto.getEmail(), userEntity.getEmail());
    }

    @Test
    public void convertToSubscriberDtoTest(){
        UserEntity userEntity = new UserEntity();
        userEntity.setName("name");
        userEntity.setLastName("lastname");
        userEntity.setEmail("email");
        SubscriberDto subscriberDto = ConvertUtil.convert(userEntity);
        Assert.assertEquals(userEntity.getName(), subscriberDto.getName());
        Assert.assertEquals(userEntity.getLastName(), subscriberDto.getLastName());
        Assert.assertEquals(userEntity.getEmail(), subscriberDto.getEmail());
    }

    @Test
    public void convertToSubscriberDtosTest(){
        List<UserEntity> userEntities = getUserEntities(4);
        List<SubscriberDto> subscriberDtos = ConvertUtil.convert(userEntities);
        Assert.assertEquals(userEntities.size(), subscriberDtos.size());
    }

    private List<UserEntity> getUserEntities(int size) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("name");
        userEntity.setLastName("lastname");
        userEntity.setEmail("email");
        List<UserEntity>userEntities = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            userEntities.add(userEntity);
        }
        return userEntities;
    }
}
