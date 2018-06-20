package com.kixeye.analytics.offertool.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@EqualsAndHashCode
public class Unit
{
    Unit(int id, String name, Faction faction, String classification, int generation)
    {
        this.id = id;
        this.name = name;
        this.faction = faction;
        this.classification = classification;
        this.generation = generation;
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
        Buildable ofClassification(String classification);
    }

    public interface Buildable
    {
        Buildable ofGeneration(int generation);

        Unit build();
    }


    static class Builder implements RequiresName, RequiresFaction, RequiresClassification, Buildable
    {
        private final int id;
        private String name;
        private String classification;
        private Faction faction;
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
        public Buildable ofClassification(String classification)
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
            Unit unit = new Unit(this.id, this.name, this.faction, this.classification, this.generation);
            return unit;
        }
    }

}
