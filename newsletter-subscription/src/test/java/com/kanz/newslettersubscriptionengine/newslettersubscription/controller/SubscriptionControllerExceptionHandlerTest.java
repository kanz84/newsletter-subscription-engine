package com.kanz.newslettersubscriptionengine.newslettersubscription.controller;

import com.kanz.newslettersubscriptionengine.newslettersubscription.NewsletterSubscriptionApplication;
import com.kanz.newslettersubscriptionengine.newslettersubscription.repo.UserRepository;
import com.kanz.newslettersubscriptionengine.newslettersubscription.service.UserService;
import com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestSubscriptionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestDataGenerator
        .createSubscribeRequestDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewsletterSubscriptionApplication.class)
@WebAppConfiguration
public class SubscriptionControllerExceptionHandlerTest {

    @Autowired
    private TestSubscriptionManager subscriptionManager;

    @MockBean
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @After
    public void after(){
        userRepository.deleteAll();
    }

    @Before
    public void setup() throws Exception {
//        Mockito.when(userService.createUser(any())).thenThrow(new RuntimeException());
        doThrow(RuntimeException.class).when(userService).createUser(any());
        this.subscriptionManager.setup();
    }

    @Test
    public void subscribeTest() throws Exception {
        subscriptionManager.subscribeInternalServerError(createSubscribeRequestDto());
    }

}
