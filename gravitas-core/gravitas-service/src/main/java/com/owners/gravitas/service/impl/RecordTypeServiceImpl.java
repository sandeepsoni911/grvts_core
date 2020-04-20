package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.CRMQuery.GET_RECORD_TYPE_ID;
import static com.owners.gravitas.constants.Constants.NAME;
import static com.owners.gravitas.constants.Constants.OBJECTTYPE;
import static com.owners.gravitas.constants.Constants.QUERY_PARAM_ID;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static java.lang.String.join;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.RecordTypeService;

/**
 * @author ankusht
 * 
 *         The Class RecordTypeServiceImpl.
 */
@Service
public class RecordTypeServiceImpl implements RecordTypeService {

    /** The crm query service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /**
     * The Constant cache which contains CRM record ids mapped against object
     * types.
     */
    private static final Map< String, String > cache = new HashMap<>();

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.RecordTypeService#getRecordTypeIdByName(java
     * .lang.String)
     */
    @Override
    public String getRecordTypeIdByName( final String recordTypeName, final String objectType ) {
        final String key = join( EMPTY, objectType.trim(), recordTypeName.trim() );
        String recordTypeId = cache.get( key );
        if (isBlank( recordTypeId )) {
            final QueryParams params = new QueryParams();
            params.add( OBJECTTYPE, objectType );
            params.add( NAME, recordTypeName );
            recordTypeId = convertObjectToString(
                    crmQueryService.findOne( GET_RECORD_TYPE_ID, params ).get( QUERY_PARAM_ID ) );
            cache.put( key, recordTypeId );
        }
        return recordTypeId;
    }

}
