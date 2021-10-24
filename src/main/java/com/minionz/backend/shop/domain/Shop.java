package com.minionz.backend.shop.domain;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.BaseEntity;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.controller.dto.ShopTableRequestDto;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "shop_id"))
@Entity
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

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ShopTable> tableList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CongestionStatus congestionStatus = CongestionStatus.SMOOTH;

    @Column(nullable = false)
    private int numberOfTables;

    @Builder
    public Shop(Long id, LocalDateTime createDate, LocalDateTime lastModifiedDate, String name, Owner owner, Address address, String telNumber, List<ShopTable> tableList) {
        super(id, createDate, lastModifiedDate);
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.tableList = tableList;
        setOwner(owner);
    }

    public void update(ShopRequestDto shopRequestDto) {
        this.name = shopRequestDto.getName();
        this.address = shopRequestDto.getAddress();
        this.telNumber = shopRequestDto.getTelNumber();
        this.tableList = shopRequestDto.getTableList()
                .stream()
                .map(ShopTableRequestDto::toEntity)
                .collect(Collectors.toList());
        this.numberOfTables = tableList.size();
    }

    public void mapShopWithTable() {
        for (ShopTable table : tableList) {
            table.setShop(this);
        }
    }

    public void setTableNumber() {
        AtomicInteger tableNumber = new AtomicInteger(1);
        tableList.forEach(table -> table.setTableNumber(tableNumber.getAndIncrement()));
    }

    public void makeShopTable(List<ShopTableRequestDto> shopTableRequestDtos) {
        tableList = shopTableRequestDtos.stream()
                .map(ShopTableRequestDto::toEntity)
                .collect(Collectors.toList());
        numberOfTables = tableList.size();
    }

    public void updateDegreeOfCongestion() {
        double ratioOfCongestion = getNumberOfUsingTables() / (double) numberOfTables;
        if (ratioOfCongestion < 0.3) {
            congestionStatus = CongestionStatus.SMOOTH;
        } else if (ratioOfCongestion >= 0.3 && ratioOfCongestion < 0.7) {
            congestionStatus = CongestionStatus.NORMAL;
        } else {
            congestionStatus = CongestionStatus.CONGESTED;
        }
    }

    private void setOwner(Owner owner) {
        this.owner = owner;
        owner.getShops().add(this);
    }

    private int getNumberOfUsingTables() {
        return (int) tableList.stream()
                .filter(status -> status.getUseStatus() == UseStatus.USING)
                .count();
    }
}
