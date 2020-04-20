package com.owners.gravitas.dataprep.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.dataprep.business.DataPrepBusinessService;
import com.owners.gravitas.dataprep.dto.AgentAssignmentDataDto;
import com.owners.gravitas.dataprep.dto.MarketDto;

/**
 * The Class DataPrepController.
 * 
 * @author ankusht
 */
@RestController
public class DataPrepController {

    /** The data prep business service. */
    @Autowired
    private DataPrepBusinessService dataPrepBusinessService;

    /**
     * Map agents to zips.
     *
     * @param dataDtos
     *            the data dtos
     */
    @RequestMapping( value = "/dataprep/agent-assgn", method = POST, consumes = APPLICATION_JSON_VALUE )
    public void mapAgentsToZips( @RequestBody final List< AgentAssignmentDataDto > dataDtos ) {
        dataPrepBusinessService.mapAgentsToZips( dataDtos );
    }

    /**
     * Gets the agents by zip.
     *
     * @param zip
     *            the zip
     * @return the agents by zip
     */
    @RequestMapping( value = "/dataprep/getAgents/{zip}", method = GET )
    public List< AgentAssignmentDataDto > getAgentsByZip( @PathVariable final String zip ) {
        return dataPrepBusinessService.getAgentsByZip( zip );
    }

    /**
     * Gets the market.
     *
     * @param zip
     *            the zip
     * @return the market
     */
    @RequestMapping( value = "/dataprep/getMarket/{zip}", method = GET )
    public MarketDto getMarket( @PathVariable final String zip ) {
        return dataPrepBusinessService.getMarket( zip );
    }

    /**
     * Gets the servable zips.
     *
     * @param email
     *            the email
     * @return the servable zips
     */
    @RequestMapping( value = "/dataprep/getServableZips/{email}", method = GET )
    public List< String > getServableZips( @PathVariable final String email ) {
        return dataPrepBusinessService.getServableZips( email );
    }
}
