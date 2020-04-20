package com.owners.gravitas.business.builder.domain;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.HASH_FUNCTION;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserAddress;
import com.google.api.services.admin.directory.model.UserEmail;
import com.google.api.services.admin.directory.model.UserName;
import com.google.api.services.admin.directory.model.UserPhone;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.request.AgentOnboardRequest;

/**
 * The Class GoogleUserBuilder.
 *
 * @author harshads
 */
@Component
public class GoogleUserBuilder extends AbstractBuilder<User, AgentOnboardRequest> {

	/** The default password. */
	@Value("${owners.com.default.password}")
	private String defaultPassword;

	/** The disabled password. */
	@Value("${owners.com.disabled.password}")
	private String disabledPassword;

	/** The Constant DISABLE_EMAIL_PREFIX. */
	private final static String DISABLE_EMAIL_PREFIX = "disabled_";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
	 * Object, java.lang.Object)
	 */
	@Override
	public AgentOnboardRequest convertTo(final User source, final AgentOnboardRequest destination) {
		throw new UnsupportedOperationException("convertFrom operation is not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
	 * lang.Object, java.lang.Object)
	 */
	@Override
	public User convertFrom(final AgentOnboardRequest source, final User destination) {
		User user = destination;
		String password = defaultPassword;
		String primaryEmail = source.getEmail();
		if (source != null) {
			if (user == null) {
				user = new User();
				user.setName(getAgentName(source.getFirstName(), source.getLastName()));
				user.setPassword(password);
				user.setHashFunction(HASH_FUNCTION);
				user.setChangePasswordAtNextLogin(TRUE);
			}
			if ("inactive".equals(source.getStatus())) {
				password = disabledPassword;
				user.setPassword(password);
				user.setHashFunction(HASH_FUNCTION);
				primaryEmail = DISABLE_EMAIL_PREFIX + primaryEmail;
			}
			user.setAddresses(getAgentAddress(source.getAddress()));
			user.setPhones(getAgentPhones(source.getPhone()));
			user.setPrimaryEmail(primaryEmail);
			user.setEmails(getAgentEmails(primaryEmail, source.getPersonalEmail()));
			final Map<String, Map<String, Object>> customSchemas = new HashMap<>();
			final Map<String, Object> otherFieldsSchema = new HashMap<>();
			otherFieldsSchema.put("bioData", source.getBiodata());
			customSchemas.put("otherFields", otherFieldsSchema);
			user.setCustomSchemas(customSchemas);

		}
		return user;
	}

	/**
	 * Gets the agent emails.
	 *
	 * @param ownersEmail
	 *            the owners email
	 * @param personalEmail
	 *            the personal email
	 * @return the agent emails
	 */
	private List<UserEmail> getAgentEmails(final String ownersEmail, final String personalEmail) {
		final List<UserEmail> emails = new ArrayList<>();

		final UserEmail personal = new UserEmail();
		personal.setAddress(personalEmail);
		personal.setType("other");
		personal.setPrimary(FALSE);

		final UserEmail owners = new UserEmail();
		owners.setAddress(ownersEmail);
		owners.setType("work");
		owners.setPrimary(TRUE);

		emails.add(personal);
		emails.add(owners);
		return emails;
	}

	/**
	 * Gets the agent phones.
	 *
	 * @param phone
	 *            the phone
	 * @return the agent phones
	 */
	private List<UserPhone> getAgentPhones(final String phone) {
		final UserPhone agentPhone = new UserPhone();
		agentPhone.setPrimary(TRUE);
		agentPhone.setValue(phone);
		agentPhone.setType("work");
		final List<UserPhone> phones = new ArrayList<>();
		phones.add(agentPhone);
		return phones;
	}

	/**
	 * Gets the agent address.
	 *
	 * @param address
	 *            the address
	 * @return the agent address
	 */
	private List<UserAddress> getAgentAddress(final com.owners.gravitas.dto.UserAddress address) {
		UserAddress agentAddress = null;
		final List<UserAddress> addresses = new ArrayList<>();
		if (null != address) {
			agentAddress = new UserAddress();
			agentAddress.setStreetAddress(address.getAddress1());
			agentAddress.setCountry(address.getAddress2());
			agentAddress.setLocality(address.getCity());
			agentAddress.setRegion(address.getState());
			agentAddress.setPostalCode(address.getZip());
			agentAddress.setType("home");
			addresses.add(agentAddress);
		}
		return addresses;
	}

	/**
	 * Gets the agent name.
	 *
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @return the agent name
	 */
	private UserName getAgentName(final String firstName, final String lastName) {
		final UserName userName = new UserName();
		userName.setGivenName(firstName);
		userName.setFamilyName(lastName);
		userName.setFullName(firstName + BLANK_SPACE + lastName);
		return userName;
	}
}
