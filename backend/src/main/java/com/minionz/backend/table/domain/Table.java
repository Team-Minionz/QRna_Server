package com.minionz.backend.table.domain;

import com.minionz.backend.common.domain.BaseEntity;
import com.minionz.backend.shop.domain.Shop;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "TABLES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Table extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TABLE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ID")
    private Shop shop;

    private int maxCapacity;

    @Enumerated(value = EnumType.STRING)
    private UseStatus useStatus;

    @Builder
    public Table(Shop shop, int maxCapacity, UseStatus useStatus) {
        setShop(shop);
        this.maxCapacity = maxCapacity;
        this.useStatus = useStatus;
    }


    public void setShop(Shop shop) {
        this.shop = shop;
        shop.getTableList().add(this);
    }
}
