package com.kanz.newslettersubscriptionengine.common.factory;

import com.kanz.newslettersubscriptionengine.common.dto.ResponseDto;
import com.kanz.newslettersubscriptionengine.common.entity.MyResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Date;


public class Factory {
    public static <T extends ResponseDto> MyResponseEntity<T> createResponse(T responseDto, HttpStatus status) {
        MyResponseEntity<T> response = new MyResponseEntity<>();
        response.setHttpStatus(status);
        response.setResponseDto(responseDto);
        responseDto.setTimestamp(new Date());
        return response;
    }

}
