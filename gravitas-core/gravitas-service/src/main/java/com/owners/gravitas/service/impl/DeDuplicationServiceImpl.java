/**
 *
 */
package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.CRMQuery.GET_LEAD_ID_BY_EMAIL_AND_RECORD_TYPE_ID;
import static com.owners.gravitas.constants.CRMQuery.GET_LEAD_ID_BY_PARAMETERS;
import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.constants.Constants.RECORD_TYPE_ID;
import static com.owners.gravitas.enums.CRMObject.LEAD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.dao.SearchDao;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.DeDuplicationService;
import com.owners.gravitas.service.RecordTypeService;

/**
 * The Class DeDuplicationServiceImpl.
 *
 * @author harshads
 */
@Service( "deDuplicationService" )
public class DeDuplicationServiceImpl implements DeDuplicationService {

    /** The crm service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /** The search dao. */
    @Autowired
    private SearchDao searchDao;

    /** The record type service. */
    @Autowired
    private RecordTypeService recordTypeService;

    /**
     * De duplicate lead.
     *
     * @param email
     *            the email
     * @return the CRM response
     */
    @Override
    public CRMResponse deDuplicateLead( final String email ) {
        final QueryParams params = new QueryParams();
        params.add( EMAIL, email );
        return crmQueryService.findAll( GET_LEAD_ID_BY_PARAMETERS, params );
    }

    /**
     * Gets the de duplicated lead.
     *
     * @param email
     *            the email
     * @param type
     *            the type
     * @return the CRM response
     */
    @Override
    public CRMResponse getDeDuplicatedLead( final String email, final RecordType type ) {
        final String recordTypeId = recordTypeService.getRecordTypeIdByName( type.getType(), LEAD.getName() );
        final QueryParams params = new QueryParams();
        params.add( EMAIL, email );
        params.add( RECORD_TYPE_ID, recordTypeId );
        return crmQueryService.findAll( GET_LEAD_ID_BY_EMAIL_AND_RECORD_TYPE_ID, params );
    }

    /**
     * Gets the search by email.
     *
     * @param email
     *            the email
     * @return the search by email
     */
    @Override
    public Search getSearchByContactEmail( final String contactEmail ) {
        return searchDao.searchByContactEmail( contactEmail );
    }
}
