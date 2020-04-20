/**
 *
 */
package com.owners.gravitas.delegate.buyerFarming;

import static com.owners.gravitas.enums.GravitasProcess.INSIDE_SALES_FARMING_PROCESS;

import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.delegate.BaseServiceTask;

/**
 * @author vishwanathm
 *
 */
@Service( "prepareForInsideSaleProcessServiceTask" )
public class PrepareForInsideSaleProcessServiceTask extends BaseServiceTask {

    /** The process management business service. */
    @Autowired
    protected ProcessBusinessService processBusinessService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.delegate.BaseServiceTask#execute(org.flowable.engine.
     * delegate.DelegateExecution)
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        final LeadSource leadSource = getLeadSource( execution.getId() );
        try {
            processBusinessService.createProcess( leadSource.getEmail(), leadSource.getId(), execution.getId(),
                    INSIDE_SALES_FARMING_PROCESS );
        } catch ( final Exception e ) {
            setVariablesInParentProcess( execution, leadSource, e, INSIDE_SALES_FARMING_PROCESS );
        }
    }
}
