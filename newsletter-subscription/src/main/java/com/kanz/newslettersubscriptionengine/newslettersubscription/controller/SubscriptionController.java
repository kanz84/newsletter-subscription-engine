package com.kanz.newslettersubscriptionengine.newslettersubscription.controller;

import com.kanz.newslettersubscriptionengine.common.controller.BaseController;
import com.kanz.newslettersubscriptionengine.common.dto.ResponseDto;
import com.kanz.newslettersubscriptionengine.common.entity.MyResponseEntity;
import com.kanz.newslettersubscriptionengine.newslettersubscription.dto.*;
import com.kanz.newslettersubscriptionengine.newslettersubscription.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Api(value = "", description = "Endpoint for subscription management")
@RestController
public class SubscriptionController extends BaseController {

    public static final String PARAM_NAME_EMAIL = "email";
    public static final String PARAM_NAME_PAGE = "page";
    public static final String PARAM_NAME_FROM = "from";
    public static final String PARAM_NAME_TO = "to";
    @Autowired
    private UserService userService;
    @ApiOperation(value = "Subscribes the new user", notes = "Creates new user in system with given subscriber info",
            response = SubscribeResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful creating of user", response = SubscribeResponseDto.class),
            @ApiResponse(code = 409, message = "Subscriber with given email already exists"),
            @ApiResponse(code = 422, message = "Provided input param is unprocessable"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping(value = "${url.path.controller.subscription.subscribe}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends ResponseDto> subscribe(
            @ApiParam(value = "Subscribe request dto.", required = true)
            @RequestBody SubscribeRequestDto subscribeRequestDto) {
        logger.info("Subscribe method is called. The input param is : {}", subscribeRequestDto);
        MyResponseEntity<SubscribeResponseDto> response = userService.createUser(subscribeRequestDto);
        logger.info("Subscribe method result is : {}", response);
        return createResponseEntity(response);
    }

    @ApiOperation(value = "Deletes the previously subscribed user", notes = "Deletes the subscriber with given email",
            response = UnsubscribeResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful deleting of user", response = UnsubscribeResponseDto.class),
            @ApiResponse(code = 422, message = "Provided input param is unprocessable"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @DeleteMapping(value = "${url.path.controller.subscription.unsubscribe}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends ResponseDto> unsubscribe(
            @ApiParam(value = "Unsubscribe request dto.", required = true)
            @RequestBody UnsubscribeRequestDto unsubscribeRequestDto) {
        logger.info("Unsubscribe method is called. The input param is : {}", unsubscribeRequestDto);
        MyResponseEntity<UnsubscribeResponseDto> response = userService.deleteUser(unsubscribeRequestDto);
        logger.info("Unsubscribe method result is : {}", response);
        return createResponseEntity(response);
    }

    @ApiOperation(value = "Gives the user subscription status", notes = "Gives the subscription status of user " +
            "with given email", response = IsSubscribedResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful deleting of user", response = IsSubscribedResponseDto.class),
            @ApiResponse(code = 422, message = "Provided input param is unprocessable"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(value = "${url.path.controller.subscription.isSubscribed}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends ResponseDto> isSubscribed(
            @ApiParam(value = "Email address of user for check status.", required = true)
            @RequestParam(PARAM_NAME_EMAIL) String email) {
        logger.info("IsSubscribed method is called. The input param is : email={}", email);
        MyResponseEntity<IsSubscribedResponseDto> response = userService.existUser(email);
        logger.info("IsSubscribed method result is : {}", response);
        return createResponseEntity(response);
    }

    @ApiOperation(value = "Gives subscribers list", notes = "Gives the list of previously subscribed users", response = FetchSubscribersResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful fetching the subsribers", response = FetchSubscribersResponseDto.class),
            @ApiResponse(code = 422, message = "Provided input param is unprocessable"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(value = "${url.path.controller.subscription.fetchSubscribers}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends ResponseDto> fetchSubscribers(
                                                                  @ApiParam(value = "Fetched subscribers page index number.", required = true)
                                                                  @RequestParam(value = PARAM_NAME_PAGE) int page,
                                                                  @ApiParam(value = "Fetched subscribers from date. For example : 2018-01-01T12:15:00.000Z")
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                  @RequestParam(value = PARAM_NAME_FROM, required = false) Date from,
                                                                  @ApiParam(value = "Fetched subscribers to date. For example : 2018-01-01T12:15:00.000Z")
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                  @RequestParam(value = PARAM_NAME_TO, required = false) Date to) {
        FetchSubscribersRequestDto fetchSubscribersRequestDto = createFetchSubscribersRequestDto(page, from, to);
        logger.info("FetchSubscribers method is called. The input param is : {}", fetchSubscribersRequestDto);
        MyResponseEntity<FetchSubscribersResponseDto> response = userService.findUsers(fetchSubscribersRequestDto);
        logger.info("FetchSubscribers method result is : {}", response);
        return createResponseEntity(response);
    }

    private FetchSubscribersRequestDto createFetchSubscribersRequestDto(int page, Date from, Date to) {
        FetchSubscribersRequestDto fetchSubscribersRequestDto = new FetchSubscribersRequestDto();
        fetchSubscribersRequestDto.setPage(page);
        fetchSubscribersRequestDto.setFrom(from);
        fetchSubscribersRequestDto.setTo(to);
        return fetchSubscribersRequestDto;
    }
}
