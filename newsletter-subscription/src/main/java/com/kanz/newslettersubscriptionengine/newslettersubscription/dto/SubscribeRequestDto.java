package com.kanz.newslettersubscriptionengine.newslettersubscription.dto;

import com.kanz.newslettersubscriptionengine.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class SubscribeRequestDto extends BaseDto {
    @ApiModelProperty(position = 1, required = true, value = "Subscriber first name.")
    private String name;
    @ApiModelProperty(position = 2, required = true, value = "Subscriber last name.")
    private String lastName;
    @ApiModelProperty(position = 3, required = true, value = "Subscriber email address.")
    private String email;

    public SubscribeRequestDto() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
