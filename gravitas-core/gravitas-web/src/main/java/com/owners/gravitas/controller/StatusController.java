package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.Constants.OK_STATUS;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class StatusController support the status actions.
 */
@Controller
public class StatusController {

    /**
     * Gets the status.
     *
     * @return the status
     */
    @RequestMapping( value = "/lbstatus", method = GET )
    public ResponseEntity< byte[] > getStatus() {
        return new ResponseEntity< byte[] >( OK_STATUS.getBytes(), OK );
    }

}
