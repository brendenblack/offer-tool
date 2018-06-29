//package com.kixeye.analytics.offertool.infrastructure.repositories;
//
//import com.kixeye.analytics.offertool.domain.models.Offer;
//import com.kixeye.analytics.offertool.domain.offers.OfferSummary;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//
//public interface OfferRepository extends CrudRepository<Offer, Integer>
//{
//
//    @Query(value = "SELECT * FROM offers WHERE end_time > unix_timestamp(now())", nativeQuery = true)
//    Iterable<Offer> findAllActive();
//
////    @Query(value = "", nativeQuery = true)
////    OfferSummary getDetails(int offerId);
//}
