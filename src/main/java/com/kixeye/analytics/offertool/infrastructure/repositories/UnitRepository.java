package com.kixeye.analytics.offertool.infrastructure.repositories;

import com.kixeye.analytics.offertool.domain.UnitClassifications;
import com.kixeye.analytics.offertool.domain.models.Faction;
import com.kixeye.analytics.offertool.domain.models.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class UnitRepository
{
    private final Logger log = LoggerFactory.getLogger(UnitRepository.class);
    private final FactionRepository factionRepository;

    private List<Unit> units = new ArrayList<>();

    UnitRepository(FactionRepository factionRepository)
    {
        log.debug("Initializing units repository");
        this.factionRepository = factionRepository;
        doReload();
    }

    private long lastUpdate;

    public LocalDateTime getLastUpdate()
    {
        if (lastUpdate <= 0)
        {
            return LocalDateTime.MAX;
        }
        else
        {
            return Instant.ofEpochMilli(this.lastUpdate)
                    .atZone(ZoneOffset.systemDefault())
                    .toLocalDateTime();
        }
    }

    public void doReload()
    {
        log.debug("Updating unit repository");

        BufferedReader br = null;
        String line = "";
        String splitter = ",";
        boolean skipFirstLine = true;
        try
        {
            File file = ResourceUtils.getFile("classpath:unit_id_lookup.csv");
            br = new BufferedReader(new FileReader(file));

            if (skipFirstLine)
            {
                br.readLine();
            }

            while ((line = br.readLine()) != null)
            {
                String[] entry = line.split(splitter);
                //ID,TokenID,Unit,Faction,Type,Gen,Classification,Bundles,UsedByMonetized,PctWin,DmgRatio,AvgLevel
                int id = Integer.parseInt(entry[0]);

                String name = entry[2];

                String factionName = entry[3];


                int generation;

                switch (entry[5])
                {
                    case "1":
                        generation = 1;
                        break;
                    case "2":
                        log.debug("Unit {} is gen 2", id);
                        generation = 2;
                        break;
                    case "3":
                        log.debug("Unit {} is gen 3", id);
                        generation = 3;
                        break;
                    default:
                        generation = 0;
                        break;
                }

                String classification;
                switch (entry[6].toUpperCase())
                {
                    case "AIRCRAFT":
                        classification = UnitClassifications.AIRCRAFT;
                        break;
                    case "VEHICLE":
                        classification = UnitClassifications.VEHICLE;
                        break;
                    case "INFANTRY":
                    default:
                        classification = UnitClassifications.INFANTRY;
                        break;
                }

                Faction faction = this.factionRepository.findByName(factionName).orElse(null);

                Unit.Builder builder = (Unit.Builder) Unit.withId(id)
                        .named(name)
                        .belongsTo(faction)
                        .ofClassification(classification);

                String type;
                switch (entry[4].toUpperCase())
                {
                    case "HERO":
                        builder.isHero();
                        break;
                    case "UNIQUE":
                        builder.isUnique();
                        break;
                    case "SPECIAL FORCES":
                        builder.isSpecialForces();
                        break;
                    case "STANDARD":
                    default:
                        builder.isStandard();
                        break;
                }



                Unit unit = builder.ofGeneration(generation).build();
                this.units.add(unit);
            }
        }
        catch (IOException e)
        {
            log.error("An error occurred while reading resource file", e);
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    log.error("Error while closing the buffered reader", e);
                }
            }
        }

        log.debug("Loaded {} units", this.units.size());
    }

    public List<Unit> findAll()
    {
        return this.units;
    }

    public Optional<Unit> findById(int id)
    {
        return this.units.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

}
