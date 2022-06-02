package com.kanz.newslettersubscriptionengine.common.controller;

import com.kanz.newslettersubscriptionengine.common.dto.ResponseDto;
import com.kanz.newslettersubscriptionengine.common.entity.MyResponseEntity;
import com.kanz.newslettersubscriptionengine.common.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static com.kanz.newslettersubscriptionengine.common.util.EmptyUtil.isNull;


public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected <T extends ResponseDto>ResponseEntity<T> createResponseEntity(MyResponseEntity<T> dto) {
        return ResponseEntity.status(dto.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(dto.getResponseDto());
    }

    protected <T extends ResponseDto>ResponseEntity<T> createResponseEntity(T dto, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(dto);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<? extends ResponseDto> handleException(Exception e, HttpServletRequest request) {
        String requestBody = getRequestBody(request);
        logger.error("Exception is happened in rest service for request with path ({}) and body : {}", request
                .getRequestURI(), requestBody, e);
        return createResponseEntity(new ResponseDto(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<? extends ResponseDto> handleException(TransactionSystemException e, HttpServletRequest
            request) {
        String requestBody = getRequestBody(request);
        logger.error("Exception is happened in rest service for request with path ({}) and body : {}", request
                .getRequestURI(), requestBody, e);
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        if (!Utility.isConstraintViolationException(e)) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return createResponseEntity(new ResponseDto(), httpStatus);
    }

    private String getRequestBody(HttpServletRequest request) {
        ServletInputStream inputStream = getInputStream(request);
        if (isNull(inputStream)) return "";
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        return in.lines().collect(Collectors.joining());
    }

    private ServletInputStream getInputStream(HttpServletRequest request) {
        try {
            return request.getInputStream();
        } catch (IOException e) {
            logger.error("Exception in reading the request body for request with path : {}", request.getRequestURI(),
                    e);
        }
        return null;
    }

}
