package com.owners.gravitas.service.builder;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.repository.ActionRepository;

/**
 * The Class OpportunityActionBuilderTest.
 *
 * @author raviz
 */
public class OpportunityActionBuilderTest extends AbstractBaseMockitoTest {

    /** The opportunity action builder. */
    @InjectMocks
    private OpportunityActionBuilder opportunityActionBuilder;

    /** The action repository. */
    @Mock
    private ActionRepository actionRepository;

    /**
     * Test convert to should return actions when source is not null.
     */
    @Test
    public void testConvertToShouldReturnActionsWhenSourceIsNotNull() {
        final ActionGroup source = new ActionGroup();
        final List< Action > actions = new ArrayList<>();
        actions.add( new Action() );
        source.setActions( actions );
        when( actionRepository.findByActionName( anyString() ) )
                .thenReturn( new com.owners.gravitas.domain.entity.Action() );
        final List< OpportunityAction > response = opportunityActionBuilder.convertTo( source );
        assertNotNull( response );
        verify( actionRepository ).findByActionName( anyString() );
    }

    /**
     * Test convert to should return actions when source is not null and actions
     * are empty.
     */
    @Test
    public void testConvertToShouldReturnActionsWhenSourceIsNotNullAndActionsAreEmpty() {
        final ActionGroup source = new ActionGroup();
        when( actionRepository.findByActionName( anyString() ) )
                .thenReturn( new com.owners.gravitas.domain.entity.Action() );
        final List< OpportunityAction > response = opportunityActionBuilder.convertTo( source );
        assertNotNull( response );
        verifyZeroInteractions( actionRepository );
    }

    /**
     * Test convert to should return actions when source is not null when
     * destination is passed.
     */
    @Test
    public void testConvertToShouldReturnActionsWhenSourceIsNotNullWhenDestinationIsPassed() {
        final ActionGroup source = new ActionGroup();
        final List< Action > actions = new ArrayList<>();
        actions.add( new Action() );
        source.setActions( actions );
        when( actionRepository.findByActionName( anyString() ) )
                .thenReturn( new com.owners.gravitas.domain.entity.Action() );
        final List< OpportunityAction > response = opportunityActionBuilder.convertTo( source, new ArrayList<>() );
        assertNotNull( response );
        verify( actionRepository ).findByActionName( anyString() );
    }

    /**
     * Test convert to should return nulls when source is null.
     */
    @Test
    public void testConvertToShouldReturnNullsWhenSourceIsNull() {
        final ActionGroup source = new ActionGroup();
        final List< Action > actions = new ArrayList<>();
        actions.add( new Action() );
        source.setActions( actions );
        final List< OpportunityAction > response = opportunityActionBuilder.convertTo( null );
        assertNull( response );
        verifyZeroInteractions( actionRepository );
    }

    /**
     * Test convert from should throw exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromShouldThrowException() {
        opportunityActionBuilder.convertFrom( new ArrayList<>() );
    }

}
