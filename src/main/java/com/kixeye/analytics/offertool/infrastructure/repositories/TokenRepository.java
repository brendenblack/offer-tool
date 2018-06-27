package com.kixeye.analytics.offertool.infrastructure.repositories;

import com.kixeye.analytics.offertool.domain.models.Token;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository
{
    List<Token> findAll();

    List<Token> findByUnitId(int unitId);
}
