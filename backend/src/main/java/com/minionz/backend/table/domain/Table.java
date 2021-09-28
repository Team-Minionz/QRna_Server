package com.minionz.backend.table.domain;

import com.minionz.backend.common.domain.BaseEntity;
import com.minionz.backend.shop.domain.Shop;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@javax.persistence.Table(name = "TABLES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "TABLE_ID"))
public class Table extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ID")
    private Shop shop;

    private int maxCapacity;

    @Enumerated(value = EnumType.STRING)
    private UseStatus useStatus;

    @Builder
    public Table(Long id, LocalDateTime createDate, LocalDateTime lastModifiedDateShop,Shop shop, int maxCapacity, UseStatus useStatus) {
        super(id, createDate, lastModifiedDateShop);
        setShop(shop);
        this.maxCapacity = maxCapacity;
        this.useStatus = useStatus;
    }


    public void setShop(Shop shop) {
        this.shop = shop;
        shop.getTableList().add(this);
    }
}
