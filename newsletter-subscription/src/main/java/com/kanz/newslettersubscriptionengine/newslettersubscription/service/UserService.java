package com.kanz.newslettersubscriptionengine.newslettersubscription.service;

import com.kanz.newslettersubscriptionengine.common.entity.MyResponseEntity;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.*;


public interface UserService {
    MyResponseEntity<SubscribeResponseDto> createUser(SubscribeRequestDto subscribeRequestDto);
    MyResponseEntity<UnsubscribeResponseDto> deleteUser(UnsubscribeRequestDto unsubscribeRequestDto);

    MyResponseEntity<IsSubscribedResponseDto> existUser(String email);

    MyResponseEntity<FetchSubscribersResponseDto> findUsers(FetchSubscribersRequestDto fetchSubscribersReq);
}
