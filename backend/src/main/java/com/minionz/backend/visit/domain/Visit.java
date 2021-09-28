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
@AttributeOverride(name = "id", column = @Column(name = "visit_id"))
public class Visit extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
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
