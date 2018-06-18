package com.kixeye.analytics.offertool.infrastructure.repositories;

import com.kixeye.analytics.offertool.domain.models.UserOffer;
import org.springframework.data.repository.CrudRepository;

public interface UserOfferRepository extends CrudRepository<UserOffer, Integer>
{
}
