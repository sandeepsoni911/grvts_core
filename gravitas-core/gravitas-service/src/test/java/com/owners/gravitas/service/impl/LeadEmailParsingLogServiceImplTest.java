package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.LeadEmailParsingLog;
import com.owners.gravitas.repository.LeadEmailParsingLogRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class LeadEmailParsingLogServiceImplTest.
 *
 * @author amits
 */
public class LeadEmailParsingLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The action log service impl. */
    @InjectMocks
    private LeadEmailParsingLogServiceImpl emailParsingLogServiceImpl;

    /** The lead email parsing log repository. */
    @Mock
    private LeadEmailParsingLogRepository leadEmailParsingLogRepository;

    /**
     * Test save lead email parsing.
     */
    @Test
    public void testSaveLeadEmailParsing() {
        LeadEmailParsingLog emailParsingLog = new LeadEmailParsingLog();
        Mockito.when( leadEmailParsingLogRepository.save( emailParsingLog ) ).thenReturn( emailParsingLog );
        emailParsingLog = emailParsingLogServiceImpl.save( emailParsingLog );
        Mockito.verify( leadEmailParsingLogRepository ).save( emailParsingLog );
    }

}
