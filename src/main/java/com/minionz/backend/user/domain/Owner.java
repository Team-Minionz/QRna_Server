package com.minionz.backend.user.domain;

import com.minionz.backend.shop.domain.Shop;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "owner_id"))
public class Owner extends UserBaseEntity {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Shop> shops = new ArrayList<>();

    @Builder
    public Owner(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String name, String email, String password, String telNumber, Shop shop) {
        super(id, createdDate, modifiedDate, name, email, password, telNumber);
        setShops(shop);
    }

    private void setShops(Shop shop) {
        shops.add(shop);
    }
}
