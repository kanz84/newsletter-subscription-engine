package com.kanz.newslettersubscriptionengine.common.entity;

import com.kanz.newslettersubscriptionengine.common.dto.BaseDto;
import com.kanz.newslettersubscriptionengine.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;


public class MyResponseEntity<R extends ResponseDto> extends BaseDto {
    private HttpStatus httpStatus;
    private R responseDto;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public R getResponseDto() {
        return responseDto;
    }

    public void setResponseDto(R responseDto) {
        this.responseDto = responseDto;
    }
}
