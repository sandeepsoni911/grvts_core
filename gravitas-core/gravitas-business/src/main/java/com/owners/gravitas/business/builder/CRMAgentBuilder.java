package com.owners.gravitas.business.builder;

import static com.owners.gravitas.util.DateUtil.toSqlDate;

import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class CRMAgentBuilder.
 *
 * @author madhav
 */
@Component( "CRMAgentBuilder" )
public class CRMAgentBuilder extends AbstractBuilder< AgentSource, AgentDetails > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentDetails convertTo( final AgentSource source, final AgentDetails destination ) {
        AgentDetails response = destination;
        if (source != null) {
            if (response == null) {
                response = new AgentDetails();
            }
            response.setAvailability( source.isAvailable() );
            response.setHomeZip( source.getHomeZip() );
            response.setLicense( source.getLicense() );
            response.setMobileCarrier( source.getMobileCarrier() );
            response.setState( source.getState() );
            response.setDrivingRadius( Integer.parseInt( source.getDrivingRadius() ) );
            response.setStartingOn( toSqlDate( DateUtil.toString( source.getStartingDate(), DateUtil.DEFAULT_CRM_DATE_PATTERN ), DateUtil.DEFAULT_CRM_DATE_PATTERN ) );
        }
        return response;
    }

    /* Method Not Supported */
    @Override
    public AgentSource convertFrom( final AgentDetails source, final AgentSource destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
