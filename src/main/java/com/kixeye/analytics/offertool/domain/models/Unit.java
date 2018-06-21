package com.kixeye.analytics.offertool.domain.models;

import com.kixeye.analytics.offertool.domain.UnitTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ToString
@EqualsAndHashCode
public class Unit
{
    private final Logger log = LoggerFactory.getLogger(Unit.class);

    Unit(int id, String name, Faction faction, String classification, int generation, String type)
    {
        log.debug("Constructing unit [id: {}] [name: {}] [gen: {}]", id, name, generation);
        this.id = id;
        this.name = name;
        this.faction = faction;
        this.classification = classification;
        this.generation = generation;
        this.type = type;
    }

    @Getter
    private final int id;

    @Getter
    private String name;

    @Getter
    private Faction faction;

    @Getter
    private String classification;

    @Getter
    private int generation;

    @Getter
    private String type;



    public static RequiresName withId(int id)
    {
        Builder builder = new Builder(id);
        return builder;
    }

    public interface RequiresName
    {
        RequiresFaction named(String name);
    }

    public interface RequiresFaction
    {
        RequiresClassification belongsTo(Faction faction);
    }

    public interface RequiresClassification
    {
        RequiresType ofClassification(String classification);
    }

    public interface RequiresType
    {
        Buildable isHero();

        Buildable isUnique();

        Buildable isStandard();

        Buildable isSpecialForces();

        Buildable ofType(String type);
    }

    public interface Buildable
    {
        Buildable ofGeneration(int generation);

        Unit build();
    }


    public static class Builder implements RequiresName, RequiresFaction, RequiresClassification, RequiresType, Buildable
    {
        private final int id;
        private String name;
        private String classification;
        private Faction faction;
        private String type;
        private int generation = 0;

        Builder(int id)
        {
            this.id = id;
        }

        public Builder named(String name)
        {
            this.name = name;
            return this;
        }

        @Override
        public RequiresClassification belongsTo(Faction faction)
        {
            this.faction = faction;
            return this;
        }

        @Override
        public RequiresType ofClassification(String classification)
        {
            this.classification = classification;
            return this;
        }

        @Override
        public Buildable ofGeneration(int generation)
        {
            this.generation = generation;
            return this;
        }

        @Override
        public Unit build()
        {
            Unit unit = new Unit(this.id, this.name, this.faction, this.classification, this.generation, this.type);
            return unit;
        }

        @Override
        public Buildable isHero() {
            this.type = UnitTypes.HERO;
            return this;
        }

        @Override
        public Buildable isUnique() {
            this.type = UnitTypes.UNIQUE;
            return this;
        }

        @Override
        public Buildable isStandard()
        {
            this.type = UnitTypes.STANDARD;
            return this;
        }

        @Override
        public Buildable isSpecialForces() {
            this.type = UnitTypes.SPECIAL_FORCES;
            return this;
        }

        @Override
        public Buildable ofType(String type) {
            return null;
        }
    }

}
