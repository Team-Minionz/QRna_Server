package com.minionz.backend.shop.domain;

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

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Visit> visitList = new ArrayList<>();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopTable> tableList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CongestionStatus congestionStatus = CongestionStatus.SMOOTH;

    @Column(nullable = false)
    private int numberOfTables;

    @Builder
    public Shop(Long id, LocalDateTime createDate, LocalDateTime lastModifiedDate, String name, Address address, String telNumber, List<ShopTable> tableList, int numberOfTables) {
        super(id, createDate, lastModifiedDate);
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.tableList = tableList;
        this.numberOfTables = numberOfTables;
    }

    public void mapShopWithTable() {
        for (ShopTable table : tableList) {
            table.setShop(this);
        }
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

    private int getNumberOfUsingTables() {
        return (int) tableList.stream()
                .filter(status -> status.getUseStatus() == UseStatus.USING)
                .count();
    }
}
