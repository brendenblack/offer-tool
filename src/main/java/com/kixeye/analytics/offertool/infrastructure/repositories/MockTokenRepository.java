//package com.kixeye.analytics.offertool.infrastructure.repositories;
//
//import com.kixeye.analytics.offertool.domain.UnitTypes;
//import com.kixeye.analytics.offertool.domain.models.Token;
//import com.kixeye.analytics.offertool.domain.models.Unit;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Deprecated
//public class MockTokenRepository implements TokenRepository
//{
//    private final Context context;
//    private final Logger log = LoggerFactory.getLogger(TokenRepository.class);
//
//    @Autowired
//    MockTokenRepository(Context context)
//    {
//        this.context = context;
//    }
//
//
//    private List<Token> tokens = new ArrayList<>();
//
//
//    public List<Token> findAll()
//    {
//        if (this.tokens.size() <= 0)
//        {
//            for (Unit unit : this.context.getUnits().findAll())
//            {
//                if (unit.getType() == UnitTypes.STANDARD || unit.getType() == UnitTypes.UNIQUE)
//                {
//                    Token token = new Token();
//                    String faction = unit.getFaction().getId().toLowerCase();
//
//                    // take a wild stab at the token
//                    String tokenName = "unit_upgrade_" + faction + "_" + unit.getName().toLowerCase();
//                }
//            }
//        }
//
//
//        return this.tokens;
//    }
//
//    @Override
//    public List<Token> findByUnitId(int unitId)
//    {
//        if (this.tokens.size() <= 0)
//        {
//            findAll();
//        }
//
//        return this.tokens.stream()
//                .filter(t -> t.getUnitId() == unitId)
//                .collect(Collectors.toList());
//    }
//
//}
