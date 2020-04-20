package com.owners.gravitas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.dto.GravitasHealthStatus;

/**
 * Test SystemHealthController for setting up the API gateway for
 * system health checkup.
 *
 * @author ankusht
 */
@RestController
public class SystemHealthController extends BaseController {

    /** The gravitas systems health business service. */
    @Autowired
    private SystemHealthBusinessService systemHealthBusinessService;

    /**
     * Gets the gravitas system health state.
     *
     * @return the gravitas system health state
     */
    @CrossOrigin
    @RequestMapping( value = "/system-health", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public List< GravitasHealthStatus > getGravitasSystemHealthState() {
        return systemHealthBusinessService.getGravitasHealthStatus();
    }
}
