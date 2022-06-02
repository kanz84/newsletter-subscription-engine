package com.kanz.newslettersubscriptionengine.newslettersubscription.dto;

import com.kanz.newslettersubscriptionengine.common.dto.ResponseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel
public class FetchSubscribersResponseDto extends ResponseDto {
    @ApiModelProperty(position = 1, value = "List of fetched subscribers.")
    private List<SubscriberDto> subscribers;

    public FetchSubscribersResponseDto() {
    }

    public void setSubscribers(List<SubscriberDto> subscribers) {
        this.subscribers = subscribers;
    }

    public List<SubscriberDto> getSubscribers() {
        return subscribers;
    }
}
