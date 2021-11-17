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

    @Query(value = "select * from shop where shop_name like %:name% and city like %:city%", nativeQuery = true)
    List<Shop> findByNameContainsAndAddress_CityContains(@Param("name") String name, @Param("city") String city);
}
