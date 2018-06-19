package com.kixeye.analytics.offertool.infrastructure.repositories;

import com.kixeye.analytics.offertool.domain.models.UserOffer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserOfferRepository extends CrudRepository<UserOffer, Integer>
{
    List<UserOffer> findByOfferId(int offerId);
}
