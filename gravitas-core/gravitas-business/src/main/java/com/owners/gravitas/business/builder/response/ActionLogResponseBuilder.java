/**
 *
 */
package com.owners.gravitas.business.builder.response;

import static com.owners.gravitas.util.DateUtil.ISO_DATE_FORMAT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.ActionLogDto;
import com.owners.gravitas.dto.response.ActionLogResponse;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class ActionLogResponseBuilder.
 *
 * @author harshads
 */
@Component
public class ActionLogResponseBuilder extends AbstractBuilder< List< ActionLog >, ActionLogResponse > {

    /**
     * Converts list of action logs to action log response.
     *
     * @param source
     *            action logs details from db.
     * @param destination
     *            action log response.
     * @return a{@link ActionLogResponse}.
     */
    @Override
    public ActionLogResponse convertTo( final List< ActionLog > source, final ActionLogResponse destination ) {
        ActionLogResponse response = destination;
        if (source != null && response == null) {
            response = new ActionLogResponse();
            final List< ActionLogDto > logs = new ArrayList<>();
            for ( final ActionLog actionLog : source ) {
                final ActionLogDto logDto = new ActionLogDto();
                logDto.setActionType( actionLog.getActionType() );
                logDto.setActionDtm( DateUtil.getLongDate(
                        DateUtil.toString( actionLog.getCreatedDate(), ISO_DATE_FORMAT ), ISO_DATE_FORMAT ) );
                logs.add( logDto );
            }
            response.setActionLogs( logs );
        }
        return response;
    }

    /**
     * Method is not supported.
     */
    @Override
    public List< ActionLog > convertFrom( ActionLogResponse source, List< ActionLog > destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
