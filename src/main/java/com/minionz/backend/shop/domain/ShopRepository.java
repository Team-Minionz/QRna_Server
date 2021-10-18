package com.minionz.backend.shop.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByTelNumber(String telNumber);
}
