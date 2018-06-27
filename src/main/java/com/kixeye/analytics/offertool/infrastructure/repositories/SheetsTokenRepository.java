package com.kixeye.analytics.offertool.infrastructure.repositories;

import com.google.api.client.util.DateTime;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.kixeye.analytics.offertool.domain.models.Token;
import com.kixeye.analytics.offertool.infrastructure.google.GoogleApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SheetsTokenRepository implements TokenRepository
{
    private final GoogleApiService googleApiService;
    private final Logger log = LoggerFactory.getLogger(SheetsTokenRepository.class);
    private final String SHEET_NAME = "Tokens";
    private final String sheetId;

    @Autowired
    SheetsTokenRepository(GoogleApiService googleApiService,
                          @Value("${kixeye.unitidlookup.sheetid}") String sheetId)
    {
        this.googleApiService = googleApiService;

        if (StringUtils.isEmptyOrWhitespace(sheetId))
        {
            throw new IllegalArgumentException("");
        }

        this.sheetId = sheetId;
    }


    private DateTime lastModified;


    /**
     * Returns the {@link DateTime} of the last edit on the Unit ID Lookup sheet specified in application.properties
     * @return
     */
    public DateTime getLastUpdate()
    {
        try
        {
            Drive.Files.Get fileRequest = this.googleApiService.getDrive()
                    .files()
                    .get(this.sheetId).setFields("id, modifiedTime");

            File file = fileRequest.execute();
            if (file != null)
            {
                return file.getModifiedTime();
            }

            log.error("No sheet was found in the target drive with id {}", this.sheetId);
        }
        catch (IOException e)
        {
            log.error("An error occurred fetching the last updated time of sheet {}", this.sheetId, e);
        }

        return new DateTime(0);
    }

    public boolean needsUpdate()
    {
        if (lastModified == null)
        {
            return true;
        }

        if (lastModified.getValue() < getLastUpdate().getValue())
        {
            return true;
        }

        return false;
    }

    private List<Token> tokens = new ArrayList<>();

    @Override
    public List<Token> findAll()
    {
        if (needsUpdate())
        {
            parseSheet();
        }

        return this.tokens;
    }

    public void parseSheet()
    {
        try
        {
            ValueRange response = this.googleApiService.getSheets()
                    .spreadsheets()
                    .values()
                    .get(this.sheetId, SHEET_NAME + "!A:C")
                    .execute();

            for (List<Object> row : response.getValues())
            {
                for (Object column : row)
                {
                    log.debug("Column: {}", column.toString());
                }
            }

        }
        catch (IOException e)
        {
            log.error("An error occurred while fetching data from {}/{}", this.sheetId, this.SHEET_NAME, e);
        }
    }

    @Override
    public List<Token> findByUnitId(int unitId)
    {
        if (needsUpdate())
        {
            parseSheet();
        }

        return this.tokens.stream()
                .filter(t -> t.getUnitId() == unitId)
                .collect(Collectors.toList());
    }
}
