package com.kanz.newslettersubscriptionengine.newslettersubscription.service;

import com.google.common.collect.Lists;
import com.kanz.newslettersubscriptionengine.common.entity.MyResponseEntity;
import com.kanz.newslettersubscriptionengine.newslettersubscription.NewsletterSubscriptionApplication;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.*;
import com.kanz.newslettersubscriptionengine.newslettersubscription.entity.UserEntity;
import com.kanz.newslettersubscriptionengine.newslettersubscription.repo.UserRepository;
import com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestDataGenerator;
import com.kanz.newslettersubscriptionengine.newslettersubscription.util.ConvertUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestDataGenerator.createFetchSubscribersRequestDto;
import static com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestDataGenerator.insertMany;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewsletterSubscriptionApplication.class)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Value("${list.users.page.size}")
    private int pageSize;

    @After
    public void after() {
        userRepository.deleteAll();
    }


    @Test
    public void createUserTest() {
        SubscribeRequestDto subscribeRequestDto = TestDataGenerator.createSubscribeRequestDto();
        MyResponseEntity<SubscribeResponseDto> response = userService.createUser(subscribeRequestDto);
        SubscribeResponseDto subscribeResponseDto = response.getResponseDto();
        Assert.assertNotNull(subscribeResponseDto);
        Assert.assertEquals(subscribeRequestDto.getEmail(), subscribeResponseDto.getUsername());
        Assert.assertEquals(HttpStatus.CREATED, response.getHttpStatus());
    }

    @Test
    public void createUserDuplicateTest() {
        SubscribeRequestDto subscribeRequestDto = TestDataGenerator.createSubscribeRequestDto();
        userService.createUser(subscribeRequestDto);
        MyResponseEntity<SubscribeResponseDto> response = userService.createUser(subscribeRequestDto);
        SubscribeResponseDto subscribeResponseDto = response.getResponseDto();
        Assert.assertNotNull(subscribeResponseDto);
        Assert.assertEquals(subscribeRequestDto.getEmail(), subscribeResponseDto.getUsername());
        Assert.assertEquals(HttpStatus.CONFLICT, response.getHttpStatus());
    }

    @Test(expected = TransactionSystemException.class)
    public void createUserWithInvalidEmailTest() {
        SubscribeRequestDto subscribeRequestDto = TestDataGenerator.createSubscribeRequestDto();
        subscribeRequestDto.setEmail("a.wrong");
        userService.createUser(subscribeRequestDto);
    }

    @Test
    @Transactional
    public void deleteUserTest() {
        SubscribeRequestDto subscribeRequestDto = TestDataGenerator.createSubscribeRequestDto();
        MyResponseEntity<SubscribeResponseDto> response = userService.createUser(subscribeRequestDto);
        SubscribeResponseDto subscribeResponseDto = response.getResponseDto();
        Assert.assertNotNull(subscribeResponseDto);
        Assert.assertEquals(subscribeRequestDto.getEmail(), subscribeResponseDto.getUsername());
        Assert.assertEquals(HttpStatus.CREATED, response.getHttpStatus());
        UnsubscribeRequestDto unsubscribeRequestDto = new UnsubscribeRequestDto();
        unsubscribeRequestDto.setEmail(subscribeResponseDto.getUsername());
        MyResponseEntity<UnsubscribeResponseDto> response2 = userService.deleteUser(unsubscribeRequestDto);
        UnsubscribeResponseDto unsubscribeResponseDto = response2.getResponseDto();
        Assert.assertNotNull(unsubscribeResponseDto);
        Assert.assertEquals(HttpStatus.OK, response2.getHttpStatus());
        Assert.assertFalse(userRepository.existsByEmail(subscribeResponseDto.getUsername()));
    }

    @Test
    public void isExistUserDuplicateTest() {
        MyResponseEntity<IsSubscribedResponseDto> response = userService.existUser(TestDataGenerator.getTestUserEmail());
        IsSubscribedResponseDto isSubscribedResponseDto = response.getResponseDto();
        Assert.assertNotNull(isSubscribedResponseDto);
        Assert.assertFalse(isSubscribedResponseDto.isStatus());
        SubscribeRequestDto subscribeRequestDto = TestDataGenerator.createSubscribeRequestDto();
        MyResponseEntity<SubscribeResponseDto> response2 = userService.createUser(subscribeRequestDto);
        SubscribeResponseDto subscribeResponseDto = response2.getResponseDto();
        Assert.assertNotNull(subscribeResponseDto);
        Assert.assertEquals(subscribeRequestDto.getEmail(), subscribeResponseDto.getUsername());
        Assert.assertEquals(HttpStatus.CREATED, response2.getHttpStatus());
        isSubscribedResponseDto = userService.existUser(TestDataGenerator.getTestUserEmail()).getResponseDto();
        Assert.assertNotNull(isSubscribedResponseDto);
        Assert.assertTrue(isSubscribedResponseDto.isStatus());
    }


    @Test
    public void findUsersTest() {
        Date now = new Date();
        Date from = DateUtils.addMinutes(now, -15);
        Date to = DateUtils.addMinutes(now, +15);

        List<UserEntity> userEntities = insertMany(userRepository, pageSize + 10);
        List<List<UserEntity>> listPartitions = Lists.partition(userEntities, pageSize);
        findUsersAndAssert(from, to, 0, listPartitions.get(0));
        findUsersAndAssert(from, to, 1, listPartitions.get(1));
    }

    private void findUsersAndAssert(Date from, Date to, int page, List<UserEntity> expected) {
        FetchSubscribersRequestDto fetchSubscribersRequestDto = createFetchSubscribersRequestDto(from, to, page);
        MyResponseEntity<FetchSubscribersResponseDto> response = userService.findUsers(fetchSubscribersRequestDto);
        FetchSubscribersResponseDto fetchSubscribersResponseDto = response.getResponseDto();
        Assert.assertNotNull(fetchSubscribersResponseDto);
        Assert.assertEquals(HttpStatus.OK, response.getHttpStatus());
        Assert.assertEquals(expected.size(), fetchSubscribersResponseDto.getSubscribers().size());
        Assert.assertEquals(ConvertUtil.convert(expected), fetchSubscribersResponseDto.getSubscribers());
    }

}
