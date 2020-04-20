package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ContactActivity;
import com.owners.gravitas.repository.ContactActivityRepository;

/**
 * The Class ContactActivityServiceImplTest.
 *
 * @author raviz
 */
public class ContactActivityServiceImplTest extends AbstractBaseMockitoTest {

    /** The contact activity service impl. */
    @InjectMocks
    private ContactActivityServiceImpl contactActivityServiceImpl;

    /** The contact activity repository. */
    @Mock
    private ContactActivityRepository contactActivityRepository;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final ContactActivity expectedContactActivity = new ContactActivity();
        when( contactActivityRepository.save( expectedContactActivity ) ).thenReturn( expectedContactActivity );
        final ContactActivity actualContactActivity = contactActivityServiceImpl.save( expectedContactActivity );
        assertEquals( expectedContactActivity, actualContactActivity );
        verify( contactActivityRepository ).save( expectedContactActivity );
    }
    
    /**
     * Test getContactActivityByUserId.
     */
    @Test
    public void testGetContactActivityByUserId() {
        final String userId = "test";
        final List< ContactActivity > expectedContactActivity = new ArrayList<>();
        final ContactActivity contactActivity = new ContactActivity();
        expectedContactActivity.add( contactActivity );
        when( contactActivityRepository.findByOwnersComIdOrderByCreatedDateDesc( userId ) ).thenReturn( expectedContactActivity );
        final List< ContactActivity > actualContactActivity = contactActivityServiceImpl.getContactActivityByUserId( userId );
        assertEquals( actualContactActivity, expectedContactActivity );
        /* For testing sorting of ContactActivity Start */
        final List< ContactActivity > sortingTestContactActivity =  new ArrayList<>();
        sortingTestContactActivity.addAll(actualContactActivity);
        sortingTestContactActivity.sort(new Comparator<ContactActivity>() {
        	@Override
			public int compare(ContactActivity o1, ContactActivity o2) {
				return o1.getCreatedDate().compareTo(o2.getCreatedDate());
			}
		});
        assertEquals(actualContactActivity,sortingTestContactActivity);
        /* For testing sorting of ContactActivity End */
        verify( contactActivityRepository ).findByOwnersComIdOrderByCreatedDateDesc( userId );
    }
}
