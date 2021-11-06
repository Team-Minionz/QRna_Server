package com.minionz.backend.shop.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByTelNumber(String telNumber);

    List<Shop> findByNameContains(String name);

    List<Shop> findByAddressCityEqualsAndNameContains(String city, String name);
}
