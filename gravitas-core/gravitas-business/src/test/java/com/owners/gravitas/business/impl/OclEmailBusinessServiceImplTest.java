package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.CRMObject.LEAD;
import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM_LOANS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.integration.mail.AbstractMailReceiver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AffiliateEmailBusinessService;
import com.owners.gravitas.business.BeanValidationService;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.builder.scraping.ocl.OclEmailDtoBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.OclEmailDto;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.LeadResponse;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.AffiliateEmailValidationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.util.PropertiesUtil;
import com.owners.gravitas.validator.LeadValidator;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

/**
 * The class OclEmailBusinessServiceImplTest.
 *
 * @author raviz
 */
public class OclEmailBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The OclEmailBusinessServiceImpl. */
    @InjectMocks
    private OclEmailBusinessServiceImpl oclEmailBusinessServiceImpl;

    /** The Ocl email builder. */
    @Mock
    private OclEmailDtoBuilder oclEmailDtoBuilder;

    /** The opportunity business service. */
    @Mock
    private OpportunityBusinessService opportunityBusinessService;

    // /** The lead service. */
    @Mock
    private LeadService leadService;

    /** The record type service. */
    @Mock
    private RecordTypeService recordTypeService;

    /** The lead business service. */
    @Mock
    private LeadBusinessService leadBusinessService;

    /** The affiliate email business service. */
    @Mock
    private AffiliateEmailBusinessService affiliateEmailBusinessService;

    /** The lead validator. */
    @Mock
    private LeadValidator leadValidator;

    /** The message. */
    private Message message;

    /** The bean validation service. */
    @Mock
    private BeanValidationService beanValidationService;

    /**
     * Before test.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeTest
    public void beforeTest() throws Exception {
        message = initMessageFolderNotExists( initMessageText() );
        final Map< String, String > props = new HashMap< String, String >();
        props.put( "ocl.loan.phase.prospect.stage", "New" );
        if (PropertiesUtil.getPropertiesMap() == null) {
            PropertiesUtil.setPropertiesMap( props );
        } else {
            PropertiesUtil.getPropertiesMap().putAll( props );
        }
    }

    /**
     * Test scrape ocl email message_should pass for valid inputs.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testScrapeOclEmailMessage_shouldPassForValidInputs() throws Exception {
        final String oclOpportunityRecordTypeId = "oclOpportunityRecordTypeId";
        final String crmOpportunityId = "crmOpportunityId";
        final OclEmailDto oclEmailDto = getValidOclEmailDto();
        final int loanNumber = Integer.parseInt( oclEmailDto.getLoanNumber() );

        when( oclEmailDtoBuilder.convertToOclEmailDto( message ) ).thenReturn( oclEmailDto );
        when( recordTypeService.getRecordTypeIdByName( OWNERS_COM_LOANS.getType(), OPPORTUNITY.getName() ) )
                .thenReturn( oclOpportunityRecordTypeId );
        when( opportunityBusinessService.getOpportunityIdByRecordTypeAndLoanNumber( oclOpportunityRecordTypeId,
                loanNumber ) ).thenReturn( crmOpportunityId );
        oclEmailBusinessServiceImpl.scrapeOclEmailMessage( message );
        verify( oclEmailDtoBuilder ).convertToOclEmailDto( message );
        verify( opportunityBusinessService ).updateOclOpportunity( crmOpportunityId, oclEmailDto.getLoanPhase(),
                loanNumber, oclOpportunityRecordTypeId );
        verify( affiliateEmailBusinessService ).addLabelToMessage( anyString(), any( Message.class ) );
        verifyZeroInteractions( leadService, leadBusinessService );
    }

    /**
     * Test scrape ocl email message_should throw affiliate email validation
     * exception_when loan number is less than nine digit.
     *
     * @throws Exception
     *             the exception
     */
    @Test( expectedExceptions = AffiliateEmailValidationException.class )
    public void testScrapeOclEmailMessage_shouldThrowException_whenLoanNumberIsLessThanNineDigit() throws Exception {

        final OclEmailDto oclEmailDto = getValidOclEmailDto();
        oclEmailDto.setLoanNumber( "1000" ); // loan number less than 9 digit

        Map< String, List< String > > constraints = new HashMap<>();
        constraints.put( "key1", new ArrayList<>() );
        when( beanValidationService.validate( oclEmailDto ) ).thenReturn( constraints );
        when( oclEmailDtoBuilder.convertToOclEmailDto( message ) ).thenReturn( oclEmailDto );

        oclEmailBusinessServiceImpl.scrapeOclEmailMessage( message );
    }

    /**
     * Test scrape ocl email message_when crm opportunity id not found for loan
     * number.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testScrapeOclEmailMessage_whenCrmOpportunityIdNotFoundForLoanNumber() throws Exception {
        final String oclLeadRecordType = "oclLeadRecordType";
        final String leadId = "leadId";
        final OclEmailDto oclEmailDto = getValidOclEmailDto();
        String oppRecordTypeId = "oppRecordTypeId";
        final int loanNumber = Integer.parseInt( oclEmailDto.getLoanNumber() );
        String crmOpportunityId = "crmOpportunityId";

        when( oclEmailDtoBuilder.convertToOclEmailDto( message ) ).thenReturn( oclEmailDto );
        when( recordTypeService.getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(), OPPORTUNITY.getName() ) )
                .thenReturn( oppRecordTypeId );
        when( opportunityBusinessService.getOpportunityIdByRecordTypeAndLoanNumber( oppRecordTypeId, loanNumber ) )
                .thenReturn( "" );
        when( recordTypeService.getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(), LEAD.getName() ) )
                .thenReturn( oclLeadRecordType );
        when( leadBusinessService.getLeadIdByRequestTypeAndEmail( oclLeadRecordType, oclEmailDto.getEmail() ) )
                .thenReturn( leadId );
        when( leadBusinessService.convertLeadToOpportunity( leadId ) ).thenReturn( crmOpportunityId );
        oclEmailBusinessServiceImpl.scrapeOclEmailMessage( message );

        verify( oclEmailDtoBuilder ).convertToOclEmailDto( message );
        verify( recordTypeService ).getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(),
                OPPORTUNITY.getName() );
        verify( recordTypeService ).getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(), LEAD.getName() );
        verify( opportunityBusinessService ).getOpportunityIdByRecordTypeAndLoanNumber( oppRecordTypeId, loanNumber );
        verify( leadBusinessService ).getLeadIdByRequestTypeAndEmail( oclLeadRecordType, oclEmailDto.getEmail() );
        verify( leadService ).updateLead( anyMap(), anyString() );
        verify( opportunityBusinessService ).updateOclOpportunity( crmOpportunityId, oclEmailDto.getLoanPhase(),
                loanNumber, oppRecordTypeId );
        verify( affiliateEmailBusinessService ).addLabelToMessage( anyString(), any( Message.class ) );
    }

    /**
     * Test scrape ocl email message_when lead id not found.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testScrapeOclEmailMessage_whenLeadIdNotFound_thenCreateOclLead() throws Exception {
        final String leadRecordTypeId = "oclLeadRecordType";
        final String leadId = "leadId";
        final OclEmailDto oclEmailDto = getValidOclEmailDto();
        final GenericLeadRequest genericLeadRequest = new GenericLeadRequest();
        final LeadResponse leadResponse = new LeadResponse( BaseResponse.Status.SUCCESS, "msg", leadId );
        String oppRecordTypeId = "oppRecordTypeId";
        final int loanNumber = Integer.parseInt( oclEmailDto.getLoanNumber() );
        String crmOpportunityId = "crmOpportunityId";

        when( oclEmailDtoBuilder.convertToOclEmailDto( message ) ).thenReturn( oclEmailDto );
        when( recordTypeService.getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(), OPPORTUNITY.getName() ) )
                .thenReturn( oppRecordTypeId );
        when( opportunityBusinessService.getOpportunityIdByRecordTypeAndLoanNumber( oppRecordTypeId, loanNumber ) )
                .thenReturn( "" );
        when( recordTypeService.getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(), LEAD.getName() ) )
                .thenReturn( leadRecordTypeId );
        when( leadBusinessService.getLeadIdByRequestTypeAndEmail( leadRecordTypeId, oclEmailDto.getEmail() ) )
                .thenReturn( "" );
        when( oclEmailDtoBuilder.convertTo( oclEmailDto ) ).thenReturn( genericLeadRequest );
        when( leadBusinessService.createLead( genericLeadRequest, false, null ) ).thenReturn( leadResponse );
        when( leadService.convertLeadToOpportunity( leadId ) ).thenReturn( crmOpportunityId );
        when( leadBusinessService.convertLeadToOpportunity( leadId ) ).thenReturn( crmOpportunityId );

        oclEmailBusinessServiceImpl.scrapeOclEmailMessage( message );

        verify( oclEmailDtoBuilder ).convertToOclEmailDto( message );
        verify( recordTypeService ).getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(),
                OPPORTUNITY.getName() );
        verify( recordTypeService ).getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(), LEAD.getName() );
        verify( oclEmailDtoBuilder ).convertTo( oclEmailDto );
        verify( affiliateEmailBusinessService ).addLabelToMessage( anyString(), any( Message.class ) );
        verify( leadBusinessService ).createLead( genericLeadRequest, false, null );
        verify( opportunityBusinessService ).getOpportunityIdByRecordTypeAndLoanNumber( oppRecordTypeId, loanNumber );
        verify( leadBusinessService ).getLeadIdByRequestTypeAndEmail( leadRecordTypeId, oclEmailDto.getEmail() );
        verifyZeroInteractions( leadService );
        verify( opportunityBusinessService ).updateOclOpportunity( crmOpportunityId, oclEmailDto.getLoanPhase(),
                loanNumber, oppRecordTypeId );
    }

    /**
     * Test scrape ocl email message_when lead id is found.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testScrapeOclEmailMessage_whenLeadIdIsFound_thenCreateOclLead() throws Exception {
        final String oclLeadRecordType = "oclLeadRecordType";
        final String leadId = "leadId";
        final OclEmailDto oclEmailDto = getValidOclEmailDto();
        final GenericLeadRequest genericLeadRequest = new GenericLeadRequest();
        final LeadResponse leadResponse = new LeadResponse( BaseResponse.Status.SUCCESS, anyString(), leadId );
        String oppRecordTypeId = "oppRecordTypeId";
        final int loanNumber = Integer.parseInt( oclEmailDto.getLoanNumber() );

        when( oclEmailDtoBuilder.convertToOclEmailDto( message ) ).thenReturn( oclEmailDto );
        when( recordTypeService.getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(), OPPORTUNITY.getName() ) )
                .thenReturn( "" );
        when( opportunityBusinessService.getOpportunityIdByRecordTypeAndLoanNumber( oppRecordTypeId, loanNumber ) )
                .thenReturn( "" );
        when( recordTypeService.getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(), LEAD.getName() ) )
                .thenReturn( oclLeadRecordType );
        when( leadService.getLeadIdByEmailAndRecordTypeId( oclEmailDto.getEmail(), oclLeadRecordType ) )
                .thenThrow( ResultNotFoundException.class );
        when( oclEmailDtoBuilder.convertTo( oclEmailDto ) ).thenReturn( genericLeadRequest );
        when( leadBusinessService.createLead( genericLeadRequest, false, null ) ).thenReturn( leadResponse );
        when( leadService.convertLeadToOpportunity( leadId ) ).thenReturn( "" );
        doNothing().when( opportunityBusinessService ).updateOclOpportunity( "", oclEmailDto.getLoanPhase(),
                Integer.parseInt( oclEmailDto.getLoanNumber() ), "" );
        doNothing().when( affiliateEmailBusinessService ).addLabelToMessage( anyString(), any( Message.class ) );

        oclEmailBusinessServiceImpl.scrapeOclEmailMessage( message );

        verify( oclEmailDtoBuilder ).convertToOclEmailDto( message );
        verify( recordTypeService ).getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(),
                OPPORTUNITY.getName() );
        verify( recordTypeService ).getRecordTypeIdByName( RecordType.OWNERS_COM_LOANS.getType(), LEAD.getName() );
        verify( oclEmailDtoBuilder ).convertTo( oclEmailDto );
        verify( affiliateEmailBusinessService ).addLabelToMessage( anyString(), any( Message.class ) );
        verify( leadBusinessService ).createLead( genericLeadRequest, false, null );
    }

    /**
     * Init the message text.
     *
     * @return the string
     */
    private String initMessageText() {
        return "";
    }

    /**
     * Init message.
     *
     * @param messageText
     *            the message text
     * @return the mime message
     * @throws Exception
     *             the exception
     */
    private MimeMessage initMessageFolderNotExists( final String messageText ) throws Exception {
        final MimeMessage message = mock( MimeMessage.class );
        final InternetAddress fromAddress = new InternetAddress( "test <noReply@hubzu.com>" );
        final Address[] address = { fromAddress };
        when( message.getFrom() ).thenReturn( address );
        when( message.getAllRecipients() ).thenReturn( address );
        when( message.getSubject() ).thenReturn( "test" );
        when( message.getReceivedDate() ).thenReturn( new Date() );
        message.setText( messageText );
        message.setContent( messageText, "text/html" );
        final Field folderField = AbstractMailReceiver.class.getDeclaredField( "folder" );
        folderField.setAccessible( true );
        final Folder folder = mock( IMAPFolder.class );
        when( message.getFolder() ).thenReturn( folder );
        final IMAPStore store = mock( IMAPStore.class );
        when( folder.getStore() ).thenReturn( store );
        when( store.getFolder( anyString() ) ).thenReturn( folder );
        when( folder.exists() ).thenReturn( false );
        return message;
    }

    /**
     * Gets valid OclEmailDto object.
     *
     * @return OclEmailDto
     */
    private OclEmailDto getValidOclEmailDto() {
        final OclEmailDto oclEmailDto = new OclEmailDto();
        oclEmailDto.setEmail( "xyz@owners.com" );
        oclEmailDto.setLoanNumber( "100000001" );
        oclEmailDto.setFirstName( "firstName" );
        oclEmailDto.setLastName( "lastName" );
        oclEmailDto.setLoanPhase( "Prospect" );
        return oclEmailDto;
    }
}
