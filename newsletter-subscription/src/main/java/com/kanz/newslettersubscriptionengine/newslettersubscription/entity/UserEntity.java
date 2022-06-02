package com.kanz.newslettersubscriptionengine.newslettersubscription.entity;

import com.kanz.newslettersubscriptionengine.common.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import java.util.Objects;


@Entity
@Table(name = "tbl_user", uniqueConstraints = @UniqueConstraint(name = "uix_usr_email", columnNames = "email"))
public class UserEntity extends BaseEntity {
    public UserEntity() {
    }

    private String name;

    private String lastName;
    @Email
    private String email;

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
        if (!super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        //@formatter:off
        return Objects.equals(name, that.name) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email);
        //@formatter:on
    }
}
