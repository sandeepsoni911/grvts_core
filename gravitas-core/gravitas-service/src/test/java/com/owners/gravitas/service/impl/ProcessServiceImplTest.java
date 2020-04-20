package com.owners.gravitas.service.impl;

import static com.owners.gravitas.enums.GravitasProcess.INSIDE_SALES_FARMING_PROCESS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.repository.ProcessRepository;

/**
 * The Class ProcessServiceImplTest.
 *
 * @author raviz
 */
public class ProcessServiceImplTest extends AbstractBaseMockitoTest {

    /** The process service impl. */
    @InjectMocks
    private ProcessServiceImpl processServiceImpl;

    /** The process management repository. */
    @Mock
    private ProcessRepository processManagementRepository;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final Process expectedProcess = new Process();
        when( processManagementRepository.save( expectedProcess ) ).thenReturn( expectedProcess );
        final Process actualProcess = processServiceImpl.save( expectedProcess );
        assertEquals( actualProcess, expectedProcess );
        verify( processManagementRepository ).save( expectedProcess );
    }

    /**
     * Test get process.
     */
    @Test
    public void testGetProcess() {
        final String email = "test";
        final GravitasProcess processCode = INSIDE_SALES_FARMING_PROCESS;
        final String status = "test";
        final Process expectedProcess = new Process();
        when( processManagementRepository.findByEmailAndProcessCodeAndStatus( email, processCode.name(), status ) )
                .thenReturn( expectedProcess );
        final Process actualProcess = processServiceImpl.getProcess( email, processCode, status );
        assertEquals( actualProcess, expectedProcess );
        verify( processManagementRepository ).findByEmailAndProcessCodeAndStatus( email, processCode.name(), status );
    }

    /**
     * Test get process with execution id.
     */
    @Test
    public void testGetProcessWithExecutionId() {
        final String email = "test";
        final GravitasProcess processCode = INSIDE_SALES_FARMING_PROCESS;
        final String status = "test";
        final String executionId = "testexecutionid";
        final Process expectedProcess = new Process();

        when( processManagementRepository.findByEmailAndProcessCodeAndStatusAndExecutionId( email, processCode.name(),
                status, executionId ) ).thenReturn( expectedProcess );

        final Process actualProcess = processServiceImpl.getProcess( email, processCode, status, executionId );
        assertEquals( actualProcess, expectedProcess );
        verify( processManagementRepository ).findByEmailAndProcessCodeAndStatusAndExecutionId( email,
                processCode.name(), status, executionId );
    }
}
