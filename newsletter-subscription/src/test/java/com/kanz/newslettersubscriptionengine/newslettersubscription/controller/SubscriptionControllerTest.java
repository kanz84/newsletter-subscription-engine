package com.kanz.newslettersubscriptionengine.newslettersubscription.controller;

import com.google.common.collect.Lists;
import com.kanz.newslettersubscriptionengine.newslettersubscription.NewsletterSubscriptionApplication;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.FetchSubscribersResponseDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.IsSubscribedResponseDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.SubscribeRequestDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.SubscribeResponseDto;
import com.kanz.newslettersubscriptionengine.newslettersubscription.entity.UserEntity;
import com.kanz.newslettersubscriptionengine.newslettersubscription.repo.UserRepository;
import com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestDataGenerator;
import com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestSubscriptionManager;
import com.kanz.newslettersubscriptionengine.newslettersubscription.util.ConvertUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestDataGenerator
        .createSubscribeRequestDto;
import static com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestDataGenerator
        .createUnsubscribeRequestDto;
import static com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestDataGenerator.insertMany;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewsletterSubscriptionApplication.class)
@WebAppConfiguration
public class SubscriptionControllerTest {

    @Autowired
    TestSubscriptionManager subscriptionManager;

    @Autowired
    UserRepository userRepository;

    @Value("${list.users.page.size}")
    private int pageSize;

    @After
    public void after(){
        userRepository.deleteAll();
    }

    @Before
    public void setup() throws Exception {
        subscriptionManager.setup();
    }

    @Test
    public void subscribeTest() throws Exception {
        subscriptionManager.subscribe(createSubscribeRequestDto());
    }

    @Test
    public void subscribeWithInvalidEmailTest() throws Exception {
        SubscribeRequestDto subscribeRequestDto = createSubscribeRequestDto();
        subscribeRequestDto.setEmail("a.wrong");
        subscriptionManager.subscribeUnprocessableEntity(subscribeRequestDto);
    }

    @Test
    public void subscribeUnsubscribeTest() throws Exception {
        SubscribeResponseDto subscribeResponseDto = subscriptionManager.subscribe(createSubscribeRequestDto());
        Assert.assertEquals(TestDataGenerator.getTestUserEmail(), subscribeResponseDto.getUsername());
        subscriptionManager.unsubscribe(createUnsubscribeRequestDto());
        Assert.assertFalse(userRepository.existsByEmail(subscribeResponseDto.getUsername()));
    }

    @Test
    public void isSubscribedTest() throws Exception {
        SubscribeRequestDto subscribeRequestDto = createSubscribeRequestDto();
        IsSubscribedResponseDto isSubscribedResponseDto = subscriptionManager.isSubscribed(subscribeRequestDto.getEmail());
        Assert.assertNotNull(isSubscribedResponseDto);
        Assert.assertFalse(isSubscribedResponseDto.isStatus());
        subscriptionManager.subscribe(subscribeRequestDto);
        isSubscribedResponseDto = subscriptionManager.isSubscribed(subscribeRequestDto.getEmail());
        Assert.assertNotNull(isSubscribedResponseDto);
        Assert.assertTrue(isSubscribedResponseDto.isStatus());
    }

    @Test
    public void fetchSubscribersTest() throws Exception {
        int count = pageSize + 10;
        List<UserEntity> userEntities = insertMany(userRepository, count);
        List<List<UserEntity>> partitions = Lists.partition(userEntities, pageSize);
        fetchAndAssert(partitions.get(0), 0);
        fetchAndAssert(partitions.get(1), 1);
    }

    private void fetchAndAssert(List<UserEntity> userEntities, int page) throws Exception {
        FetchSubscribersResponseDto fetchSubscribersResponseDto = subscriptionManager.fetchSubscribers(page);
        Assert.assertNotNull(fetchSubscribersResponseDto);
        Assert.assertNotNull(fetchSubscribersResponseDto.getSubscribers());
        Assert.assertEquals(userEntities.size(), fetchSubscribersResponseDto.getSubscribers().size());
        Assert.assertEquals(ConvertUtil.convert(userEntities), fetchSubscribersResponseDto.getSubscribers());
    }

    @Test
    public void integrationTest() throws Exception {
        SubscribeRequestDto subscribeRequestDto = createSubscribeRequestDto();
        SubscribeResponseDto subscribeResponseDto = subscriptionManager.subscribe(subscribeRequestDto);
        Assert.assertEquals(subscribeRequestDto.getEmail(), subscribeResponseDto.getUsername());
        Assert.assertTrue(subscriptionManager.isSubscribed(subscribeRequestDto.getEmail()).isStatus());
        FetchSubscribersResponseDto fetchSubscribersResponseDto = subscriptionManager.fetchSubscribers(0);
        Assert.assertEquals(1, fetchSubscribersResponseDto.getSubscribers().size());
        Assert.assertEquals(subscribeRequestDto.getEmail(), fetchSubscribersResponseDto.getSubscribers().get(0).getEmail());
        subscriptionManager.unsubscribe(createUnsubscribeRequestDto());
        Assert.assertFalse(subscriptionManager.isSubscribed(subscribeRequestDto.getEmail()).isStatus());
        fetchSubscribersResponseDto = subscriptionManager.fetchSubscribers(0);
        Assert.assertEquals(0, fetchSubscribersResponseDto.getSubscribers().size());
    }

}
