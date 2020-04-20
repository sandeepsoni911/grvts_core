package com.owners.gravitas.business.impl;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.service.HostService;
import com.owners.gravitas.service.ProcessService;

/**
 * The Class ProcessBusinessServiceImplTest.
 *
 * @author raviz
 */
public class ProcessBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The process business service impl. */
    @InjectMocks
    private ProcessBusinessServiceImpl processBusinessServiceImpl;

    /** The process management service. */
    @Mock
    private ProcessService processManagementService;

    /** The host service. */
    @Mock
    private HostService hostService;

    /** The runtime service. */
    @Mock
    protected RuntimeService runtimeService;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final Process process = new Process();
        when( processManagementService.save( process ) ).thenReturn( process );
        processBusinessServiceImpl.save( process );
        verify( processManagementService ).save( process );
    }

    /**
     * Test get process.
     */
    @Test
    public void testGetProcess() {
        final Process expectedProcess = new Process();
        final String email = "test@test.com";
        final GravitasProcess processCode = GravitasProcess.INSIDE_SALES_FARMING_PROCESS;
        final String status = "active";
        when( processManagementService.getProcess( email, processCode, status ) ).thenReturn( expectedProcess );

        final Process actualProcess = processBusinessServiceImpl.getProcess( email, processCode, status );
        assertEquals( actualProcess, expectedProcess );
        verify( processManagementService ).getProcess( email, processCode, status );
    }

    /**
     * Test de activate process should deactivate process when process is
     * available.
     */
    @Test
    public void testDeActivateProcessShouldDeactivateProcessWhenProcessIsAvailable() {
        final Process expectedProcess = new Process();
        final String email = "test@test.com";
        final String status = "active";
        final GravitasProcess processCode = GravitasProcess.INSIDE_SALES_FARMING_PROCESS;

        when( processManagementService.getProcess( email, processCode, status ) ).thenReturn( expectedProcess );
        when( processManagementService.save( expectedProcess ) ).thenReturn( expectedProcess );

        final Process actualProcess = processBusinessServiceImpl.deActivateProcess( email, processCode );
        assertEquals( actualProcess.getStatus(), "inactive" );
        verify( processManagementService ).getProcess( email, processCode, status );
        verify( processManagementService ).save( expectedProcess );
    }

    /**
     * Test de activate process should return null when process is not
     * available.
     */
    @Test
    public void testDeActivateProcessShouldReturnNullWhenProcessIsNotAvailable() {
        final String email = "test@test.com";
        final String status = "active";
        final GravitasProcess processCode = GravitasProcess.INSIDE_SALES_FARMING_PROCESS;

        when( processManagementService.getProcess( email, processCode, status ) ).thenReturn( null );

        final Process actualProcess = processBusinessServiceImpl.deActivateProcess( email, processCode );
        assertNull( actualProcess );
        verify( processManagementService ).getProcess( email, processCode, status );
        verify( processManagementService, times( 0 ) ).save( Mockito.any( Process.class ) );
    }

    /**
     * Test de activate and signal should return process when map is not empty.
     */
    @Test
    public void testDeActivateAndSignalShouldReturnProcessWhenMapIsNotEmpty() {
        final String email = "test@test.com";
        final GravitasProcess processName = GravitasProcess.INSIDE_SALES_FARMING_PROCESS;
        final String status = "active";
        final Process expectedProcess = new Process();
        final Map< String, Object > params = new HashMap<>();
        params.put( "test", "test" );

        when( processManagementService.getProcess( email, processName, status ) ).thenReturn( expectedProcess );
        when( processManagementService.save( expectedProcess ) ).thenReturn( expectedProcess );

        final Process actualProcess = processBusinessServiceImpl.deActivateAndSignal( email, processName, params );
        assertEquals( actualProcess, expectedProcess );
        verify( processManagementService ).getProcess( email, processName, status );
        verify( processManagementService ).save( expectedProcess );
        verify( runtimeService ).setVariables( expectedProcess.getExecutionId(), params );
        // verify( runtimeService ).signal( expectedProcess.getExecutionId() );
        verify( runtimeService ).trigger( expectedProcess.getExecutionId() );
        // verify( runtimeService ).signal( expectedProcess.getExecutionId() );
        verify( runtimeService ).trigger( expectedProcess.getExecutionId() );
    }

    /**
     * Test de activate and signal should return process when map is empty.
     */
    @Test
    public void testDeActivateAndSignalShouldReturnProcessWhenMapIsEmpty() {
        final String email = "test@test.com";
        final GravitasProcess processName = GravitasProcess.INSIDE_SALES_FARMING_PROCESS;
        final String status = "active";
        final Process expectedProcess = new Process();
        final Map< String, Object > params = new HashMap<>();

        when( processManagementService.getProcess( email, processName, status ) ).thenReturn( expectedProcess );
        when( processManagementService.save( expectedProcess ) ).thenReturn( expectedProcess );

        final Process actualProcess = processBusinessServiceImpl.deActivateAndSignal( email, processName, params );
        assertEquals( actualProcess, expectedProcess );
        verify( processManagementService ).getProcess( email, processName, status );
        verify( processManagementService ).save( expectedProcess );
        verify( runtimeService, times( 0 ) ).setVariables( expectedProcess.getExecutionId(), params );
        // verify( runtimeService ).signal( expectedProcess.getExecutionId() );
        verify( runtimeService ).trigger( expectedProcess.getExecutionId() );
        // verify( runtimeService ).signal( expectedProcess.getExecutionId() );
        verify( runtimeService ).trigger( expectedProcess.getExecutionId() );
    }

    /**
     * Test de activate and signal should return null when process not found.
     */
    @Test
    public void testDeActivateAndSignalShouldReturnNullWhenProcessNotFound() {
        final String email = "test@test.com";
        final GravitasProcess processName = GravitasProcess.INSIDE_SALES_FARMING_PROCESS;
        final String status = "active";
        final Map< String, Object > params = new HashMap<>();

        when( processManagementService.getProcess( email, processName, status ) ).thenReturn( null );

        final Process actualProcess = processBusinessServiceImpl.deActivateAndSignal( email, processName, params );
        assertNull( actualProcess );
        verify( processManagementService ).getProcess( email, processName, status );
        verify( processManagementService, times( 0 ) ).save( Mockito.any( Process.class ) );
        verify( runtimeService, times( 0 ) ).setVariables( anyString(), anyMap() );
        // verify( runtimeService, times( 0 ) ).signal( anyString() );
        verify( runtimeService, times( 0 ) ).trigger( anyString() );
        // verify( runtimeService, times( 0 ) ).signal( anyString() );
        verify( runtimeService, times( 0 ) ).trigger( anyString() );
    }

    /**
     * Test create process should createprocess.
     */
    @Test
    public void testCreateProcessShouldCreateprocess() {
        final String email = "test@test.com";
        final String crmId = "test";
        final String executionId = "test";
        final String status = "active";
        final GravitasProcess processName = GravitasProcess.INSIDE_SALES_FARMING_PROCESS;

        processBusinessServiceImpl.createProcess( email, crmId, executionId, processName );

        //verify( processManagementService ).getProcess( email, processName, status );
        verify( processManagementService ).save( Mockito.any( Process.class ) );
        verify( hostService ).getHost();
    }

    /**
     * Test get process with execution id.
     */
    @Test
    public void testGetProcessWithExecutionId() {
        final Process expectedProcess = new Process();
        final String email = "test@test.com";
        final String executionId = "testexecutionid";
        final GravitasProcess processCode = GravitasProcess.INSIDE_SALES_FARMING_PROCESS;
        final String status = "active";

        when( processManagementService.getProcess( email, processCode, status, executionId ) )
                .thenReturn( expectedProcess );

        final Process actualProcess = processBusinessServiceImpl.getProcess( email, processCode, status, executionId );

        assertEquals( actualProcess, expectedProcess );
        verify( processManagementService ).getProcess( email, processCode, status, executionId );
    }

    /**
     * Test de activate process with execution id should deactivate process when
     * process is available.
     */
    @Test
    public void testDeActivateProcessWithExecutionIdShouldDeactivateProcessWhenProcessIsAvailable() {
        final Process expectedProcess = new Process();
        final String email = "test@test.com";
        final String status = "active";
        final String executionId = "testexecutionid";
        final GravitasProcess processCode = GravitasProcess.INSIDE_SALES_FARMING_PROCESS;

        when( processManagementService.getProcess( email, processCode, status, executionId ) )
                .thenReturn( expectedProcess );
        when( processManagementService.save( expectedProcess ) ).thenReturn( expectedProcess );

        final Process actualProcess = processBusinessServiceImpl.deActivateProcess( email, processCode, executionId );

        assertEquals( actualProcess.getStatus(), "inactive" );
        verify( processManagementService ).getProcess( email, processCode, status, executionId );
        verify( processManagementService ).save( expectedProcess );
    }

    /**
     * Test create process without crm id should createprocess.
     */
    @Test
    public void testCreateProcessWithoutCrmIdShouldCreateprocess() {
        final String email = "test@test.com";
        final String executionId = "test";
        final GravitasProcess processName = GravitasProcess.INSIDE_SALES_FARMING_PROCESS;

        when( processManagementService.save( Mockito.any( Process.class ) ) ).thenReturn( new Process() );
        when( hostService.getHost() ).thenReturn( "testhost" );

        processBusinessServiceImpl.createProcess( email, executionId, processName );

        verify( processManagementService ).save( Mockito.any( Process.class ) );
        verify( hostService ).getHost();
    }

}
