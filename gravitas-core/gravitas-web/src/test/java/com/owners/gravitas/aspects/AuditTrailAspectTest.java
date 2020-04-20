package com.owners.gravitas.aspects;

import java.lang.annotation.Annotation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.aspect.AuditTrailAspect;
import com.owners.gravitas.business.AuditTrailBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.enums.ActionEntity;
import com.owners.gravitas.enums.ActionType;

/**
 * The Class AuditTrailAspectTest.
 *
 * @author vishwanathm
 */
public class AuditTrailAspectTest extends AbstractBaseMockitoTest {

    /** The audit trail aspect. */
    @InjectMocks
    private AuditTrailAspect auditTrailAspect;

    /** The join point. */
    @Mock
    private org.aspectj.lang.JoinPoint joinPoint;

    /** The audit trail business service. */
    @Mock
    private AuditTrailBusinessService auditTrailBusinessService;

    /**
     * Test audit actions.
     */
    @Test
    public void testAuditActions() {
        Object[] args = { "test" };
        Mockito.when( joinPoint.getArgs() ).thenReturn( args );
        Audit audit = new Audit() {

            @Override
            public Class< ? extends Annotation > annotationType() {
                return Audit.class;
            }

            @Override
            public ActionType type() {
                // TODO Auto-generated method stub
                return ActionType.CREATE;
            }

            @Override
            public ActionEntity entity() {
                // TODO Auto-generated method stub
                return ActionEntity.AGENT;
            }

            @Override
            public String[] args() {
                String[] args = { Constants.AGENT_ID };
                return args;
            }
        };
        Mockito.doNothing().when( auditTrailBusinessService ).createAuditLog( Mockito.any(), Mockito.any( Audit.class ),
                Mockito.any() );
        final Object object = auditTrailAspect.auditActions( joinPoint, audit, new Object() );
        Mockito.verify( auditTrailBusinessService ).createAuditLog( Mockito.any(), Mockito.any( Audit.class ),
                Mockito.any() );
        Assert.assertNotNull( object );
    }
}
