package com.minionz.backend.shop.domain;

import com.minionz.backend.table.domain.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
}
