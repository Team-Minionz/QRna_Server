package com.minionz.backend.user.domain;

import com.minionz.backend.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class UserBaseEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String telNumber;

    public UserBaseEntity(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String name, String email, String password, String telNumber) {
        super(id, createdDate, modifiedDate);
        this.name = name;
        this.email = email;
        this.password = password;
        this.telNumber = telNumber;
    }
}
