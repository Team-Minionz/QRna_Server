package com.minionz.backend.shop.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByTelNumber(String telNumber);

    @Query(value = "select * from Shop where (6371*acos(cos(radians(latitude))*cos(radians(:latitude))*cos(radians(:longitude)-radians(longitude))+sin(radians(latitude))*sin(radians(:latitude)))) < 0.5"
    , nativeQuery = true)
    List<Shop> findByNearShop(@Param("latitude") double latitude, @Param("longitude") double longitude);

    List<Shop> findByNameContains(String name);

    @Query("select s from Shop as s where s.name like '%name%' and s.address.city like '%city%'")
    List<Shop> findByNameContainsAndAddress_CityContains(@Param("name") String name, @Param("city") String city);
}
