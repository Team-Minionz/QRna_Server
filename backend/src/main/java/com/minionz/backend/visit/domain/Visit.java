package com.minionz.backend.visit.domain;

import com.minionz.backend.common.domain.BaseEntity;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "VISIT_ID"))
public class Visit extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ID")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Visit(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, Shop shop, User user) {
        super(id, createdDate, modifiedDate);
        setShop(shop);
        setUser(user);
    }

    //==연관관계 편의 메소드==//
    public void setShop(Shop shop) {
        this.shop = shop;
        shop.getVisitList().add(this);
    }

    private void setUser(User user) {
        this.user = user;
        user.getVisitList().add(this);
    }
}
