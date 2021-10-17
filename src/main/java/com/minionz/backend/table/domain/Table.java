package com.minionz.backend.table.domain;

import com.minionz.backend.common.domain.BaseEntity;
import com.minionz.backend.shop.domain.Shop;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "table_id"))
@Entity
public class Table extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(nullable = false)
    private int maxUser;

    private int countUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UseStatus useStatus;

    @Builder
    public Table(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, Shop shop, int maxUser, int countUser, UseStatus useStatus) {
        super(id, createdDate, modifiedDate);
        this.shop = shop;
        this.maxUser = maxUser;
        this.countUser = countUser;
        this.useStatus = useStatus;
    }
}
