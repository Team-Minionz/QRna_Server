package com.minionz.backend.shop.domain;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.BaseEntity;
import com.minionz.backend.visit.domain.Visit;
import com.minionz.backend.table.domain.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Shop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOP_ID")
    private Long id;

    @Column(name = "SHOP_NAME")
    private String name;

    @Embedded
    private Address address;

    private String telNumber;

    private int maxPopulation;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Table> tableList = new ArrayList<>();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Visit> visitList = new ArrayList<>();

    @Builder
    public Shop(String name, Address address, String telNumber, int maxPopulation, List<Table> tableList) {
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.maxPopulation = maxPopulation;
        this.tableList = tableList;
    }
}
