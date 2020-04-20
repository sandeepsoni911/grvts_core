package com.owners.gravitas.business.builder.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.ActionGroupDao;
import com.owners.gravitas.domain.entity.Action;
import com.owners.gravitas.domain.entity.ActionGroup;

/**
 * The Class ActionGroupBuilderTest.
 *
 * @author shivamm
 */
public class ActionGroupBuilderTest extends AbstractBaseMockitoTest {

    /** The opportunity builder. */
    @InjectMocks
    private ActionGroupBuilder actionGroupBuilder;

    /** The agent action group dao. */
    @Mock
    private ActionGroupDao agentActionGroupDao;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        String opportunityId = "test";
        ActionGroup grp = new ActionGroup();
        List< Action > actions = new ArrayList<>();
        Action action = new Action();
        actions.add( action );
        grp.setActions( actions );
        when( agentActionGroupDao.getActionGroup() ).thenReturn( grp );
        com.owners.gravitas.domain.ActionGroup opp = actionGroupBuilder.convertTo( opportunityId );
        Mockito.verify( agentActionGroupDao ).getActionGroup();
        assertEquals( opportunityId, opp.getOpportunityId() );
        Assert.assertNotNull( opp );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        actionGroupBuilder.convertFrom( new com.owners.gravitas.domain.ActionGroup() );
    }
}
