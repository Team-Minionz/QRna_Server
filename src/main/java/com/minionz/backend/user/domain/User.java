package com.minionz.backend.user.domain;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.visit.domain.Visit;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Setter
public class User extends UserBaseEntity {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Visit> visitList = new ArrayList<>();

    @Embedded
    private Address address;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Builder
    public User(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String name, String email, String password, String nickName, String telNumber, Address address) {
        super(id, createdDate, modifiedDate, name, email, password, telNumber);
        this.address = address;
        this.nickName = nickName;
    }
}
