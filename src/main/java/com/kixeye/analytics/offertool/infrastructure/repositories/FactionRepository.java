package com.kixeye.analytics.offertool.infrastructure.repositories;

import com.kixeye.analytics.offertool.domain.models.Faction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FactionRepository
{
    private List<Faction> factions = new ArrayList<>();

    FactionRepository()
    {
        Faction sen = new Faction();
        sen.setId("SEN");
        sen.setName("Sentinels");
        factions.add(sen);

        Faction sur = new Faction();
        sur.setId("SUR");
        sur.setName("Survivors");
        factions.add(sur);

        Faction cor = new Faction();
        cor.setId("COR");
        cor.setName("Corpus");
    }

    public Optional<Faction> findById(String id)
    {
        return this.factions.stream()
                .filter(f -> f.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<Faction> findByName(String name)
    {
        return this.factions.stream()
                .filter(f -> f.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Faction> findAll()
    {
        return this.factions;
    }
}
