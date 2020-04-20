package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.enums.LeadStatus;
import com.owners.gravitas.repository.ContactRepository;

/**
 * The Class ContactServiceImplV1Test.
 *
 * @author raviz
 */
public class ContactServiceImplV1Test extends AbstractBaseMockitoTest {

    /** The contact service impl v1. */
    @InjectMocks
    private ContactEntityServiceImplV1 contactServiceImplV1;

    /** The contact repository. */
    @Mock
    private ContactRepository contactRepository;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final Contact expectedContact = new Contact();
        when( contactRepository.save( expectedContact ) ).thenReturn( expectedContact );
        contactServiceImplV1.save( expectedContact );
        verify( contactRepository ).save( expectedContact );
    }

    /**
     * Test find by crm id.
     */
    @Test
    public void testFindByCrmId() {
        final Contact expectedContact = new Contact();
        final String crmId = "testId";
        when( contactRepository.findByCrmId( crmId ) ).thenReturn( expectedContact );
        final Contact actualContact = contactServiceImplV1.findByCrmId( crmId );
        assertEquals( actualContact, expectedContact );
        verify( contactRepository ).findByCrmId( crmId );

    }

    /**
     * Test get contact by email and type.
     */
    @Test
    public void testGetContactByEmailAndType() {
        final List< Contact > contactList = new ArrayList<>();
        final Contact expectedContact = new Contact();
        contactList.add( expectedContact );
        final String email = "test@test.com";
        final String recordType = "buyer";
        when( contactRepository.findByEmailAndTypeAndStageNotOrderByCreatedDateAsc( email, recordType,
                LeadStatus.LOST.getStatus() ) ).thenReturn( contactList );
        final Contact actualContact = contactServiceImplV1.getContact( email, recordType );
        assertEquals( actualContact, expectedContact );
        verify( contactRepository ).findByEmailAndTypeAndStageNotOrderByCreatedDateAsc( email, recordType,
                LeadStatus.LOST.getStatus() );
    }

    /**
     * Test get revenue of agents by rank.
     */
    @Test
    public void testGetRevenueOfAgentsByRank() {
        final String fromDate = "test";
        final String toDate = "test";
        final int rankFilter = 1;
        final List< Object[] > expectedRevenueOfAgents = new ArrayList<>();
        when( contactRepository.getRevenueByAgents( fromDate, toDate, rankFilter ) )
                .thenReturn( expectedRevenueOfAgents );
        final List< Object[] > actualRevenueOfAgents = contactServiceImplV1.getRevenueOfAgents( fromDate, toDate,
                rankFilter );
        assertEquals( actualRevenueOfAgents, expectedRevenueOfAgents );
        verify( contactRepository ).getRevenueByAgents( fromDate, toDate, rankFilter );
    }

    /**
     * Test get revenue of agents by email.
     */
    @Test
    public void testGetRevenueOfAgentsByEmail() {
        final String agentEmail = "test";
        final String fromDate = "test";
        final String toDate = "test";
        final List< Object[] > expectedRevenueOfAgents = new ArrayList<>();
        when( contactRepository.getRevenueByAgent( agentEmail, fromDate, toDate ) )
                .thenReturn( expectedRevenueOfAgents );
        final List< Object[] > actualRevenueOfAgents = contactServiceImplV1.getRevenueOfAgent( agentEmail, fromDate,
                toDate );
        assertEquals( actualRevenueOfAgents, expectedRevenueOfAgents );
        verify( contactRepository ).getRevenueByAgent( agentEmail, fromDate, toDate );
    }

    /**
     * Test get revenue of agents by agents.
     */
    @Test
    public void testGetRevenueOfAgentsByAgents() {
        final String fromDate = "test";
        final String toDate = "test";
        final List< Object[] > expectedRevenueOfAgents = new ArrayList<>();
        when( contactRepository.getRevenueByAgents( fromDate, toDate ) ).thenReturn( expectedRevenueOfAgents );
        final List< Object[] > actualRevenueOfAgents = contactServiceImplV1.getRevenueOfAgents( fromDate, toDate );
        assertEquals( actualRevenueOfAgents, expectedRevenueOfAgents );
        verify( contactRepository ).getRevenueByAgents( fromDate, toDate );
    }

    /**
     * Test get contact by owners com id should return contact.
     */
    @Test
    public void testGetContactByOwnersComIdShouldReturnContact() {
        final String ownersComId = "test";
        final Set< Contact > contacts = new HashSet<>();
        final Contact expectedContact = new Contact();
        contacts.add( expectedContact );
        when( contactRepository.findByOwnersComId( ownersComId ) ).thenReturn( contacts );
        final Contact actualContact = contactServiceImplV1.getContactByOwnersComId( ownersComId );
        assertEquals( actualContact, expectedContact );
        verify( contactRepository ).findByOwnersComId( ownersComId );
    }

    /**
     * Test get contact by owners com id should return null.
     */
    @Test
    public void testGetContactByOwnersComIdShouldReturnNull() {
        final String ownersComId = "test";
        final Set< Contact > contacts = new HashSet<>();
        when( contactRepository.findByOwnersComId( ownersComId ) ).thenReturn( contacts );
        final Contact actualContact = contactServiceImplV1.getContactByOwnersComId( ownersComId );
        assertNull( actualContact );
        verify( contactRepository ).findByOwnersComId( ownersComId );
    }

    /**
     * Test outbound attempt contacts.
     */
    @Test
    public void testOutboundAttemptContacts() {
        final String stage = "test";
        final String objectType = "lead";
        final List< Contact > expectedContacts = new ArrayList<>();
        when( contactRepository.getContactByStageAndObjectType( stage, objectType ) ).thenReturn( expectedContacts );
        final List< Contact > actualContacts = contactServiceImplV1.getOutboundAttemptContacts( stage, objectType );
        assertEquals( actualContacts, expectedContacts );
        verify( contactRepository ).getContactByStageAndObjectType( stage, objectType );
    }

    /**
     * Test get contact by email should return contact.
     */
    @Test
    public void testGetContactByEmailShouldReturnContact() {
        final String email = "test";
        final Set< Contact > contacts = new HashSet<>();
        final Contact expectedContact = new Contact();
        contacts.add( expectedContact );
        when( contactRepository.findByEmail( email ) ).thenReturn( contacts );
        final Contact actualContact = contactServiceImplV1.getContactByEmail( email );
        assertEquals( actualContact, expectedContact );
        verify( contactRepository ).findByEmail( email );
    }

    /**
     * Test get contact by email should return null.
     */
    @Test
    public void testGetContactByEmailShouldReturnNull() {
        final String email = "test";
        final Set< Contact > contacts = new HashSet<>();
        when( contactRepository.findByEmail( email ) ).thenReturn( contacts );
        final Contact actualContact = contactServiceImplV1.getContactByEmail( email );
        assertNull( actualContact );
        verify( contactRepository ).findByEmail( email );
    }

    /**
     * Test get contact by crm id.
     */
    @Test
    public void testGetContactByCrmId() {
        final String crmId = "test";
        final Contact expectedContact = new Contact();
        when( contactRepository.findByCrmId( crmId ) ).thenReturn( expectedContact );
        final Contact actualContact = contactServiceImplV1.getContactByCrmId( crmId );
        assertEquals( actualContact, expectedContact );
        verify( contactRepository ).findByCrmId( crmId );
    }
    
    @Test
    public void testGetUserName() {
        Contact contact = new Contact();
        contact.setFirstName( "Abc" );
        contact.setLastName( "Test" );
        String name = contactServiceImplV1.getUserName( contact );
        assertEquals( name, "Abc Test" );
    }
    
    @Test
    public void testGetUserNameWithNullInput() {
        String name = contactServiceImplV1.getUserName( null );
        assertEquals( name, "" );
    }
    
    @Test
    public void testGetUserNameWithFirstNameOnly() {
        Contact contact = new Contact();
        contact.setFirstName( "Abc" );
        String name = contactServiceImplV1.getUserName( contact );
        assertEquals( name, "Abc" );
    }
    
    @Test
    public void testGetUserNameWithLastNameOnly() {
        Contact contact = new Contact();
        contact.setLastName( "Test" );
        String name = contactServiceImplV1.getUserName( contact );
        assertEquals( name, "Test" );
    }
}
