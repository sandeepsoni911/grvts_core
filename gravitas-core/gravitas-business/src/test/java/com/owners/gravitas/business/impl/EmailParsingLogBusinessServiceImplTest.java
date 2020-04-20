package com.owners.gravitas.business.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.LeadEmailParsingLog;
import com.owners.gravitas.service.LeadEmailParsingLogService;

/**
 * The test class EmailParsingLogBusinessServiceImplTest
 *
 * @author raviz
 *
 */
public class EmailParsingLogBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /**
     * The Email Parsing Log Business Service Impl
     */
    @InjectMocks
    private EmailParsingLogBusinessServiceImpl emailParsingLogBusinessService;

    /** The action log service. */
    @Mock
    private LeadEmailParsingLogService leadEmailParsingLogService;

    /**
     * Test save lead email parsing log.
     *
     * @param parsingLog
     *            the parsing log
     */
    @Test
    public void testSaveLeadEmailParsingLog() {
        final LeadEmailParsingLog parsingLog = new LeadEmailParsingLog();
        when( leadEmailParsingLogService.save( parsingLog ) ).thenReturn( parsingLog );

        emailParsingLogBusinessService.saveLeadEmailParsingLog( parsingLog );

        verify( leadEmailParsingLogService ).save( any( LeadEmailParsingLog.class ) );
    }

}
