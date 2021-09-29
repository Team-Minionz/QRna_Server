package com.minionz.backend.user.domain;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.BaseEntity;
import com.minionz.backend.visit.domain.Visit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseEntity {

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String telNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Visit> visitList = new ArrayList<>();

    @Builder
    public User(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String name, String email, String password, String nickName, String telNumber, Address address) {
        super(id, createdDate, modifiedDate);
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.telNumber = telNumber;
        this.address = address;
    }
}
