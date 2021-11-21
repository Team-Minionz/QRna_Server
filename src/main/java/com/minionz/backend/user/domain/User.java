package com.minionz.backend.user.domain;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.visit.domain.Visit;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "user_id")),
        @AttributeOverride(name = "name", column = @Column(name = "user_name"))
})
@Entity
public class User extends UserBaseEntity {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Visit> visitList = new ArrayList<>();

    @Embedded
    private Address address;

    @Column(nullable = false, unique = true)
    private String nickName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Builder
    public User(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String name, String email, String password, String nickName, String telNumber, Address address) {
        super(id, createdDate, modifiedDate, name, email, password, telNumber);
        this.address = address;
        this.nickName = nickName;
    }

    public boolean checkBookmark(Long shopId) throws NotFoundException {
        return bookmarks.stream()
                .map(Bookmark::getShop)
                .anyMatch(b -> b.getId().equals(shopId));
    }

    public void deleteBookmark(Bookmark findBookmark) {
        this.getBookmarks().remove(findBookmark);
    }
}
