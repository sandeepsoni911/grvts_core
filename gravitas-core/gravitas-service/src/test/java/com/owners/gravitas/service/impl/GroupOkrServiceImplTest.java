package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.GroupOkr;
import com.owners.gravitas.repository.GroupOkrRepository;

/**
 * The Class GroupOkrServiceImplTest.
 *
 * @author raviz
 */
public class GroupOkrServiceImplTest extends AbstractBaseMockitoTest {

    /** The Group okr service impl. */
    @InjectMocks
    private GroupOkrServiceImpl groupOkrServiceImpl;

    /** The group okr repository. */
    @Mock
    private GroupOkrRepository groupOkrRepository;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final GroupOkr expectedGroupOkr = new GroupOkr();
        when( groupOkrRepository.save( expectedGroupOkr ) ).thenReturn( expectedGroupOkr );
        final GroupOkr actualGroupOkr = groupOkrServiceImpl.save( expectedGroupOkr );
        assertEquals( actualGroupOkr, expectedGroupOkr );
        verify( groupOkrRepository ).save( expectedGroupOkr );
    }
}
