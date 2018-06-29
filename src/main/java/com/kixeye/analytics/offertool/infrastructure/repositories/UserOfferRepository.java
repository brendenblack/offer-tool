//package com.kixeye.analytics.offertool.infrastructure.repositories;
//
//import com.kixeye.analytics.offertool.domain.models.UserOffer;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//
//import java.util.List;
//
//public interface UserOfferRepository extends CrudRepository<UserOffer, Integer>
//{
//    @Query(value = "SELECT * FROM user_offers uo WHERE uo.offer_id = ?1", nativeQuery = true)
//    Iterable<UserOffer> findByOfferId(int offerId);
//
//    @Query(value = "SELECT * FROM user_offers uo WHERE uo.user_id = ?1", nativeQuery = true)
//    Iterable<UserOffer> findByUserId(int userId);
//}
