package com.minionz.backend.shop.domain;

import com.minionz.backend.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "table_id"))
public class ShopTable extends BaseEntity {

    private int tableNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UseStatus useStatus = UseStatus.EMPTY;

    @Column(nullable = false)
    private int maxUser;

    private int countUser;

    @Builder
    public ShopTable(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, int tableNumber, Shop shop, int maxUser) {
        super(id, createdDate, modifiedDate);
        this.tableNumber = tableNumber;
        this.shop = shop;
        this.maxUser = maxUser;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Optional<Shop> getOptionalShop() {
        return Optional.ofNullable(this.shop);
    }

    public void use() {
        countUser++;
        useStatus = UseStatus.USING;
        shop.updateDegreeOfCongestion();
    }
}
