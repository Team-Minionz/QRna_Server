package com.minionz.backend.user.domain;

import com.minionz.backend.common.domain.BaseEntity;
import com.minionz.backend.shop.domain.Shop;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "bookmark_id"))
@Entity
public class Bookmark extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public Bookmark(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, User user, Shop shop) {
        super(id, createdDate, modifiedDate);
        setUser(user);
        this.shop = shop;
    }

    /**
     *  연관관계 편의 메소드
     */
    private void setUser(User user) {
        this.user = user;
        user.getBookmarks().add(this);
    }
}
