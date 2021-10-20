package com.minionz.backend.shop.domain;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.BaseEntity;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.user.domain.Owner;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AttributeOverride(name = "id", column = @Column(name = "shop_id"))
public class Shop extends BaseEntity {

    @Column(name = "shop_name", nullable = false)
    private String name;

    @Embedded
    private Address address;

    @Column(nullable = false, unique = true)
    private String telNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Visit> visitList = new ArrayList<>();

    @Builder
    public Shop(Long id, LocalDateTime createDate, LocalDateTime lastModifiedDate,Owner owner, String name, Address address, String telNumber) {
        super(id, createDate, lastModifiedDate);
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.owner = owner;
    }

    public void update(ShopRequestDto shopRequestDto) {
        this.name = shopRequestDto.getName();
        this.address = shopRequestDto.getAddress();
        this.telNumber = shopRequestDto.getTelNumber();
    }
}
