package com.kixeye.analytics.offertool.infrastructure.repositories;

import com.kixeye.analytics.offertool.domain.models.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface OfferRepository extends CrudRepository<Offer, Integer>
{
}
