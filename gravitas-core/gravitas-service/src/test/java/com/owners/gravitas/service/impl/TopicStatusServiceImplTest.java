package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.TopicStatus;
import com.owners.gravitas.repository.TopicStatusRepository;

/**
 * The Class TopicStatusServiceImplTest.
 * 
 * @author ankusht
 */
public class TopicStatusServiceImplTest extends AbstractBaseMockitoTest {

    /** The topic status repository. */
    @Mock
    private TopicStatusRepository topicStatusRepository;

    /** The topic status service impl. */
    @InjectMocks
    private TopicStatusServiceImpl topicStatusServiceImpl;

    /**
     * Test clear topic status data.
     */
    @Test
    public void testClearTopicStatusData() {
        topicStatusServiceImpl.clearTopicStatusData();
        verify( topicStatusRepository ).deleteAll();
    }

    /**
     * Test find topic names.
     */
    @Test
    public void testFindTopicNames() {
        Set< String > expected = new HashSet<>();
        when( topicStatusRepository.findTopicNames() ).thenReturn( expected );
        Set< String > actual = topicStatusServiceImpl.findTopicNames();
        assertEquals( expected, actual );
        verify( topicStatusRepository ).findTopicNames();
    }

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        TopicStatus topicStatus = new TopicStatus();
        TopicStatus expected = new TopicStatus();
        when( topicStatusRepository.save( topicStatus ) ).thenReturn( expected );
        TopicStatus actual = topicStatusServiceImpl.save( topicStatus );
        assertEquals( expected, actual );
        verify( topicStatusRepository ).save( topicStatus );
    }

    /**
     * Test find by name.
     */
    @Test
    public void testFindByName() {
        String topicName = "topicName";
        TopicStatus expected = new TopicStatus();
        when( topicStatusRepository.findByTopicName( topicName ) ).thenReturn( expected );
        TopicStatus actual = topicStatusServiceImpl.findByName( topicName );
        assertEquals( expected, actual );
        verify( topicStatusRepository ).findByTopicName( topicName );
    }

    /**
     * Test update topic status.
     */
    @Test
    public void testUpdateTopicStatus() {
        String name = "name";
        String status = "status";
        TopicStatus topicStatus = new TopicStatus();
        when( topicStatusRepository.findByTopicName( name ) ).thenReturn( topicStatus );
        topicStatusServiceImpl.updateTopicStatus( name, status );
        verify( topicStatusRepository ).save( topicStatus );
    }

    /**
     * Testfind all.
     */
    @Test
    public void testfindAll() {
        List< TopicStatus > expected = new ArrayList<>();
        when( topicStatusRepository.findAll() ).thenReturn( expected );
        List< TopicStatus > actual = topicStatusServiceImpl.findAll();
        assertEquals( expected, actual );
        verify( topicStatusRepository ).findAll();
    }
}
