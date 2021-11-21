package com.minionz.backend.shop.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByTelNumber(String telNumber);

    @Query(value = "select * from shop as s where (6371*acos(cos(radians(s.latitude))*cos(radians(:latitude))*cos(radians(:longitude)-radians(s.longitude))+sin(radians(s.latitude))*sin(radians(:latitude)))) < 0.5 order by (6371*acos(cos(radians(s.latitude))*cos(radians(:latitude))*cos(radians(:longitude)-radians(s.longitude))+sin(radians(s.latitude))*sin(radians(:latitude))))",
            nativeQuery = true)
    List<Shop> findByNearShop(@Param("latitude") double latitude, @Param("longitude") double longitude);

    List<Shop> findByNameContains(String name);

    List<Shop> findByNameContainsAndAddress_CityContains(String name, String city);
}
