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
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String telNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Visit> visitList = new ArrayList<>();


    @Builder
    public User(LocalDateTime createDate, LocalDateTime lastModifiedDate,String name, String nickName, String telNumber, Address address) {
        super(createDate,lastModifiedDate);
        this.name = name;
        this.nickName = nickName;
        this.telNumber = telNumber;
        this.address = address;
    }
}
