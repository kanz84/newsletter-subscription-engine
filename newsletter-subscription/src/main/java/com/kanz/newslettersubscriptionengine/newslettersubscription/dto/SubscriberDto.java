package com.kanz.newslettersubscriptionengine.newslettersubscription.dto;

import com.kanz.newslettersubscriptionengine.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;


@ApiModel
public class SubscriberDto extends BaseDto {
    @ApiModelProperty(position = 1, value = "Subscriber name.")
    private String name;
    @ApiModelProperty(position = 2, value = "Subscriber last name.")
    private String lastName;
    @ApiModelProperty(position = 3, value = "Subscriber email address.")
    private String email;

    public SubscriberDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriberDto that = (SubscriberDto) o;
        //@formatter:off
        return Objects.equals(name, that.name) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email);
        //@formatter:on
    }
}
