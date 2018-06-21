package com.kixeye.analytics.offertool.domain.models;

import com.kixeye.analytics.offertool.domain.UnitClassifications;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.Matchers.is;

public class Unit_BuilderTests
{
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void shouldSetExpectedValues()
    {
        int id = 1;
        String name = "unit name";
        Faction faction = new Faction();
        faction.setId("FAC");
        faction.setName("Faction");
        int generation = 0;

        Unit unit = Unit.withId(id)
                .named(name)
                .belongsTo(faction)
                .ofClassification(UnitClassifications.AIRCRAFT)
                .isHero()
                .ofGeneration(generation)

                .build();

        collector.checkThat(unit.getId(), is(id));
        collector.checkThat(unit.getGeneration(), is(generation));
        collector.checkThat(unit.getName(), is(name));
    }
}
