package com.minionz.backend.shop.domain;

import com.minionz.backend.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "table_id"))
@Table(name = "shop_table")
public class ShopTable extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UseStatus useStatus;

    @Column(nullable = false)
    private int maxUser;

    private int countUser;

    @Builder
    public ShopTable(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, Shop shop, int maxUser, int countUser, UseStatus useStatus) {
        super(id, createdDate, modifiedDate);
        this.shop = shop;
        this.maxUser = maxUser;
        this.countUser = countUser;
        this.useStatus = useStatus;
    }
}
