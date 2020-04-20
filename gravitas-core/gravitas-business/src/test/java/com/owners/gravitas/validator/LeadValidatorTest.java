package com.owners.gravitas.validator;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.integration.mail.AbstractMailReceiver;
import org.testng.annotations.Test;

import com.owners.gravitas.business.BeanValidationService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.AffiliateEmailValidationException;
import com.owners.gravitas.exception.InvalidArgumentException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

/**
 * The class LeadValidatorTest.
 *
 * @author raviz
 */
public class LeadValidatorTest extends AbstractBaseMockitoTest {

	/** The LeadValidator. */
	@InjectMocks
	private LeadValidator leadValidator;

    /** The bean validation service. */
    @Mock
    private BeanValidationService beanValidationService;

    /** The constraint violation. */
    @Mock
    private ConstraintViolation< Object > constraintViolation;

	/**
	 * Test validate ocl lead request should pass for valid inputs.
	 */
	@Test
	public void testValidateOCLLeadRequest_shouldPassForValidInput() {
		final GenericLeadRequest leadRequest = getValidGenericLeadRequest();
		leadValidator.validateOCLLeadRequest(leadRequest);
	}

	/**
	 * Test validate ocl lead request should throw exception on invalid inputs.
	 */
	@Test(expectedExceptions = InvalidArgumentException.class)
	public void testValidateOCLLeadRequest_shouldThrowException_onInvalidInput() {
        final GenericLeadRequest leadRequest = getValidGenericLeadRequest();
        leadRequest.setLastName( "" ); // set last name as empty
        final Set< ConstraintViolation< Object > > leadConstraintViolations = new HashSet<>();
        leadConstraintViolations.add( constraintViolation );
        when( beanValidationService.getConstraintViolations( leadRequest ) ).thenReturn( leadConstraintViolations );
        leadValidator.validateOCLLeadRequest( leadRequest );
        verify( beanValidationService ).getConstraintViolations( leadRequest );
    }

	/**
	 * Test validate unbounce lead request should pass for valid input.
	 */
	@Test
	public void testValidateUnbounceLeadRequest_shouldPassForValidInput() {
		final GenericLeadRequest leadRequest = getValidGenericLeadRequest();
		leadValidator.validateUnbounceLeadRequest(leadRequest);
	}

	/**
	 * Test validate unbounce lead request should throw exception on invalid
	 * input.
	 */
	@Test(expectedExceptions = InvalidArgumentException.class)
    public void testValidateUnbounceLeadRequest_shouldThrowException_onInvalidInput() {
        final GenericLeadRequest leadRequest = getValidGenericLeadRequest();
        leadRequest.setLastName( "" ); // set last name as empty
        final Set< ConstraintViolation< Object > > leadConstraintViolations = new HashSet<>();
        leadConstraintViolations.add( constraintViolation );
        when( beanValidationService.getConstraintViolations( leadRequest ) ).thenReturn( leadConstraintViolations );
        leadValidator.validateUnbounceLeadRequest( leadRequest );
        verify( beanValidationService ).getConstraintViolations( leadRequest );
    }

	/**
	 * Test validate unbounce lead request should throw exception on passing
	 * null LeadRequest.
	 */
	@Test(expectedExceptions = InvalidArgumentException.class)
	public void testValidateUnbounceLeadRequest_shouldThrowException_onPassingNullLeadRequest() {
		final GenericLeadRequest leadRequest = null; // null lead request
		leadValidator.validateUnbounceLeadRequest(leadRequest);
	}

	/**
	 * Test validate lead request should pass for valid input.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testValidateLeadRequest_shouldPassForValidInput() throws Exception {
		final GenericLeadRequest leadRequest = getValidGenericLeadRequest();
		leadValidator.validateLeadRequest(leadRequest, initMessageFolderNotExists("messageText"));
	}

	/**
	 * Test validate lead request should throw exception on invalid input.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test(expectedExceptions = AffiliateEmailValidationException.class)
	public void testValidateLeadRequest_shouldThrowException_onInvalidInput() throws Exception {
        final GenericLeadRequest leadRequest = getValidGenericLeadRequest();
        final Map< String, List< String > > failedContraints = new HashMap<>();
        failedContraints.put( "testKey", new ArrayList<>() );
        leadRequest.setLastName( "" );
        when( beanValidationService.validate( leadRequest ) ).thenReturn( failedContraints );
        leadValidator.validateLeadRequest( leadRequest, initMessageFolderNotExists( "messageText" ) );
        verify( beanValidationService ).validate( leadRequest );
    }

	/**
	 * Gets valid GenericLeadRequest object.
	 *
	 * @return GenericLeadRequest
	 */
	private GenericLeadRequest getValidGenericLeadRequest() {
		final GenericLeadRequest leadRequest = new GenericLeadRequest();
		leadRequest.setLastName("leadRequest");
		leadRequest.setLeadType(RecordType.HUBZU.toString());
		leadRequest.setSource("source");
		leadRequest.setEmail("test@test.com");
		leadRequest.setLeadSourceUrl("http://test.com");
		leadRequest.setRequestType(LeadRequestType.MAKE_OFFER.toString());
		return leadRequest;
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
	private MimeMessage initMessageFolderNotExists(final String messageText) throws Exception {
		final MimeMessage message = mock(MimeMessage.class);
		final InternetAddress fromAddress = new InternetAddress("test <noReply@hubzu.com>");
		final Address[] address = { fromAddress };
		when(message.getFrom()).thenReturn(address);
		when(message.getAllRecipients()).thenReturn(address);
		when(message.getSubject()).thenReturn("test");
		when(message.getReceivedDate()).thenReturn(new Date());
		message.setText(messageText);
		message.setContent(messageText, "text/html");
		final Field folderField = AbstractMailReceiver.class.getDeclaredField("folder");
		folderField.setAccessible(true);
		final Folder folder = mock(IMAPFolder.class);
		when(message.getFolder()).thenReturn(folder);
		final IMAPStore store = mock(IMAPStore.class);
		when(folder.getStore()).thenReturn(store);
		when(store.getFolder(anyString())).thenReturn(folder);
		when(folder.exists()).thenReturn(false);
		return message;
	}
}
