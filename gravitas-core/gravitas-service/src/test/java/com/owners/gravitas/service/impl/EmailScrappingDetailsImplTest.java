package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.EmailScrappingDetails;
import com.owners.gravitas.repository.EmailScrappingDetailsRepository;

/**
 * Test class for EmailScrappingDetailsImpl
 * @author sandeepsoni
 *
 */
public class EmailScrappingDetailsImplTest extends AbstractBaseMockitoTest {
	
	@InjectMocks
	EmailScrappingDetailsImpl emailScrappingDetailsImpl;
	
	@Mock
	EmailScrappingDetailsRepository emailScrappingDetailsRepository;
	
	@Test
	public void test() {
		EmailScrappingDetails emailScrappingDetails = new EmailScrappingDetails();
		Mockito.when(emailScrappingDetailsRepository.save(emailScrappingDetails)).thenReturn(emailScrappingDetails);
		emailScrappingDetails = emailScrappingDetailsImpl.save(emailScrappingDetails);
		Mockito.verify(emailScrappingDetailsRepository).save(emailScrappingDetails);
	}
	

}
