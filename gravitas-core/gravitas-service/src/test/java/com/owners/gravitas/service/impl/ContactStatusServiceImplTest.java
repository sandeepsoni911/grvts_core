package com.owners.gravitas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ContactStatus;
import com.owners.gravitas.repository.ContactStatusRepository;


/**
 * Test class for ContactStatusServiceImpl
 * @author sandeepsoni
 *
 */
public class ContactStatusServiceImplTest extends AbstractBaseMockitoTest {
	
	@InjectMocks
	ContactStatusServiceImpl contactStatusServiceImpl;
	
	@Mock
	ContactStatusRepository contactStatusRepository;
	
	@Test
	public void test_Sav() {
		ContactStatus contactStatusRequest = new ContactStatus();
		contactStatusRequest.setEmail("test@mail.com");
		contactStatusRequest.setRequestJson("some json object");
		contactStatusRequest.setRequestType("New");
		Mockito.when(contactStatusRepository.save(contactStatusRequest)).thenReturn(contactStatusRequest);
		ContactStatus response =contactStatusServiceImpl.save(contactStatusRequest);
		Assert.assertNotNull(response);
		
	}
	
	@Test
	public void testfindById() {
		ContactStatus contactStatusRequest = new ContactStatus();
		contactStatusRequest.setEmail("test@mail.com");
		contactStatusRequest.setRequestJson("some json object");
		contactStatusRequest.setRequestType("New");
		contactStatusRequest.setId("test");
		Mockito.when(contactStatusRepository.findById(Mockito.anyString())).thenReturn(contactStatusRequest);
		
		ContactStatus response =contactStatusServiceImpl.findById("test");
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testfindByEmail() {
		ContactStatus contactStatusRequest = new ContactStatus();
		contactStatusRequest.setEmail("test@mail.com");
		contactStatusRequest.setRequestJson("some json object");
		contactStatusRequest.setRequestType("New");
		contactStatusRequest.setId("test");
		Mockito.when(contactStatusRepository.findByEmail(Mockito.anyString())).thenReturn(contactStatusRequest);
		
		ContactStatus response =contactStatusServiceImpl.findByEmail("test@mail.com");
		Assert.assertNotNull(response);
	}
	
	/*@Test
	public void testgetContactStatusUnderRetryCount() {
		ContactStatus contactStatusRequest = new ContactStatus();
		contactStatusRequest.setEmail("test@mail.com");
		contactStatusRequest.setRequestJson("some json object");
		contactStatusRequest.setRequestType("New");
		contactStatusRequest.setId("test");
		List<ContactStatus> lcs = new ArrayList<ContactStatus>();
		lcs.add(contactStatusRequest);
		Mockito.when(contactStatusRepository.getContactStatusUnderRetryCount(Mockito.anyInt())).thenReturn(lcs);
		
		List<ContactStatus> response =contactStatusServiceImpl.getContactStatusUnderRetryCount(2);
		Assert.assertNotNull(response);
		Assert.assertEquals(1, response.size());
	}*/
}