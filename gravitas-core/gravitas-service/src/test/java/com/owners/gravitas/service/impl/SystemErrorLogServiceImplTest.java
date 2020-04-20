package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.SystemErrorLog;
import com.owners.gravitas.repository.SystemErrorLogRepository;

/**
 * The Class SystemErrorLogServiceImplTest.
 */
public class SystemErrorLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The system error log repository. */
    @Mock
    private SystemErrorLogRepository systemErrorLogRepository;

    /** The system error log service impl. */
    @InjectMocks
    private SystemErrorLogServiceImpl systemErrorLogServiceImpl;

    /**
     * Test save system error logs.
     */
    @Test
    public void testSaveSystemErrorLogs() {
        Collection< SystemErrorLog > systemErrorLogs = new ArrayList<>();
        systemErrorLogServiceImpl.saveSystemErrorLogs( systemErrorLogs );
        verify( systemErrorLogRepository ).save( systemErrorLogs );
    }

    /**
     * Test find all systems errors.
     */
    @Test
    public void testFindAllSystemsErrors() {
        systemErrorLogServiceImpl.findLatestSystemErrors();
        verify( systemErrorLogRepository ).findLatestSystemErrors();
    }
}
