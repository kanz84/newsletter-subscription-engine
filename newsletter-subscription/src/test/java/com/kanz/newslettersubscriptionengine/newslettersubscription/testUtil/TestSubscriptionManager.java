package com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil;

import com.kanz.newslettersubscriptionengine.newslettersubscription.controller.SubscriptionController;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;

import static com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestUtility.toDateString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Component
public class TestSubscriptionManager {
    @Value("${url.path.controller.subscription.unsubscribe}")
    private String unsubscribeUrl;

    @Value("${url.path.controller.subscription.subscribe}")
    private String subscribeUrl;

    @Value("${url.path.controller.subscription.isSubscribed}")
    private String isSubscribedUrl;

    @Value("${url.path.controller.subscription.fetchSubscribers}")
    private String fetchSubscribersUrl;

    @Autowired
    private TestMockMvc testMockMvc;

    public void setup() throws Exception {
        testMockMvc.setup();
    }

    public SubscribeResponseDto subscribe(SubscribeRequestDto subscribeRequestDto) throws Exception {
        return testMockMvc.callUrl(subscribeUrl, HttpMethod.POST, subscribeRequestDto, status().isCreated(),
                SubscribeResponseDto.class, null);
    }

    public IsSubscribedResponseDto isSubscribed(String email) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(1);
        params.add(SubscriptionController.PARAM_NAME_EMAIL, email);
        return testMockMvc.callUrl(isSubscribedUrl, HttpMethod.GET, null, status().isOk(), IsSubscribedResponseDto
                .class, params, TestDataGenerator.getTestUserEmail());
    }

    public SubscribeRequestDto subscribeUnprocessableEntity(SubscribeRequestDto subscribeRequestDto) throws Exception {
        testMockMvc.callUrl(subscribeUrl, HttpMethod.POST, subscribeRequestDto, status().isUnprocessableEntity(),
                SubscribeResponseDto.class, null);
        return subscribeRequestDto;
    }

    public SubscribeRequestDto subscribeInternalServerError(SubscribeRequestDto subscribeRequestDto) throws Exception {
        testMockMvc.callUrl(subscribeUrl, HttpMethod.POST, subscribeRequestDto, status().isInternalServerError(),
                SubscribeResponseDto.class, null);
        return subscribeRequestDto;
    }

    public void unsubscribe(UnsubscribeRequestDto unsubscribeRequestDto) throws Exception {
        testMockMvc.callUrl(unsubscribeUrl, HttpMethod.DELETE, unsubscribeRequestDto, status().isOk(),
                UnsubscribeResponseDto.class, null);
    }

    public FetchSubscribersResponseDto fetchSubscribers(int page) throws Exception {
        Date now = new Date();
        Date from = DateUtils.addMinutes(now, -15);
        Date to = DateUtils.addMinutes(now, +15);

        MultiValueMap<String, String> params = makeParams(page, from, to);
        return testMockMvc.callUrl(fetchSubscribersUrl, HttpMethod.GET, null, status().isOk(),
                FetchSubscribersResponseDto.class, params);
    }

    private MultiValueMap<String, String> makeParams(int page, Date from, Date to) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(3);
        params.add(SubscriptionController.PARAM_NAME_PAGE, String.valueOf(page));
        params.add(SubscriptionController.PARAM_NAME_FROM, toDateString(from));
        params.add(SubscriptionController.PARAM_NAME_TO, toDateString(to));
        return params;
    }

}
