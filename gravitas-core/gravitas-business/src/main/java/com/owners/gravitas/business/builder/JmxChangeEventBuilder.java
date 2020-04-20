package com.owners.gravitas.business.builder;

import static com.owners.gravitas.enums.ActionEntity.JMX;
import static com.owners.gravitas.enums.ActionType.CREATE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.JmxChangeDto;

/**
 * The Class JmxChangeEventBuilder.
 * 
 * @author ankusht
 */
@Component
public class JmxChangeEventBuilder extends AbstractBuilder< ActionLog, JmxChangeDto > {

    /** The auditor. */
    @Autowired
    private AuditorAware< String > auditor;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public JmxChangeDto convertTo( final ActionLog source, final JmxChangeDto destination ) {
        throw new UnsupportedOperationException( "convertTo operation is not supported" );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public ActionLog convertFrom( final JmxChangeDto source, final ActionLog destination ) {
        ActionLog actionLog = destination;
        if (source != null) {
            if (actionLog == null) {
                actionLog = new ActionLog();
            }
            actionLog.setActionBy( auditor.getCurrentAuditor() );
            actionLog.setActionEntity( JMX.name() );
            actionLog.setActionEntityId( source.getProperty() );
            actionLog.setActionType( CREATE.name() );
            actionLog.setFieldName( source.getProperty() );
            actionLog.setCurrentValue( source.getValue().toString() );
        }
        return actionLog;
    }
}
