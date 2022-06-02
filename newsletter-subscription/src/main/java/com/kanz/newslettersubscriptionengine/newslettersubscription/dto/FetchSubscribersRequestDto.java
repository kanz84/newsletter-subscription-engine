package com.kanz.newslettersubscriptionengine.newslettersubscription.dto;

import com.kanz.newslettersubscriptionengine.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


@ApiModel
public class FetchSubscribersRequestDto extends BaseDto {
    @ApiModelProperty(position = 1, required = true, value = "Fetched subscribers page index number.")
    private int page;
    @ApiModelProperty(position = 2, required = true, value = "Fetched subscribers from date.")
    private Date from;
    @ApiModelProperty(position = 3, required = true, value = "Fetched subscribers to date.")
    private Date to;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
