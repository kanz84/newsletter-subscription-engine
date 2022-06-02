package com.kanz.newslettersubscriptionengine.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel()
public class ResponseDto extends BaseDto {
    @ApiModelProperty(position = 1, value = "Timestamp of operation done time.")
    private Date timestamp;

    public ResponseDto() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
