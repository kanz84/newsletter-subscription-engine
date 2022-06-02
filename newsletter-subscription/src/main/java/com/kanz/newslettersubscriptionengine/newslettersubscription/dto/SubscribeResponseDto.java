package com.kanz.newslettersubscriptionengine.newslettersubscription.dto;

import com.kanz.newslettersubscriptionengine.common.dto.ResponseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class SubscribeResponseDto extends ResponseDto {
    @ApiModelProperty(position = 1, value = "Username of newly subscribed subscriber.")
    private String username;

    public SubscribeResponseDto() {
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
