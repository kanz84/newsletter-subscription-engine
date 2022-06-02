package com.kanz.newslettersubscriptionengine.newslettersubscription.dto;

import com.kanz.newslettersubscriptionengine.common.dto.ResponseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class IsSubscribedResponseDto extends ResponseDto {
    @ApiModelProperty(position = 1, value = "Subscriber subscription status.")
    private boolean status;
    public IsSubscribedResponseDto() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
