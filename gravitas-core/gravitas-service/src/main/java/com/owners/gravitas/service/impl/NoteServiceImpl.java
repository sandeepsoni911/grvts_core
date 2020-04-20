package com.owners.gravitas.service.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.dto.CrmNote;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.NoteService;

/**
 * Service class for interacting with CRM Notes
 * 
 * @author ankusht
 *
 */
@Service
public class NoteServiceImpl implements NoteService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( NoteServiceImpl.class );

    /** restTemplate for making rest call to owners. */
    @Autowired
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticator;

    /** The note url */
    @Value( value = "${salesforce.create.note.url}" )
    private String noteUrl;

    /** The rest template */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Saves note in crm
     * 
     * @param crmNote
     */
    @Override
    public void saveNote( final CrmNote crmNote ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + noteUrl;
        restTemplate
                .exchange( reqUrl, HttpMethod.POST,
                        buildRequest( createHttpHeader( crmAccess.getAccessToken() ), crmNote ), CrmNote.class )
                .getBody();
        LOGGER.debug( "Note created in salesforce, for the parentId: " + crmNote.getParentId() + ", Time is : "
                + LocalDateTime.now() );
    }

}
