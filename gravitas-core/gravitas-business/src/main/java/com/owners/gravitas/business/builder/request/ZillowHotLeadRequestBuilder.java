package com.owners.gravitas.business.builder.request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * Builder for Zillow Hot Lead
 * 
 * @author sandeepsoni
 *
 */
@Component("zillowHotLeadRequestBuilder")
public class ZillowHotLeadRequestBuilder extends AbstractBuilder<LeadRequest, GenericLeadRequest> {

	@Override
	public GenericLeadRequest convertTo(final LeadRequest source, final GenericLeadRequest destination) {
		GenericLeadRequest leadRequest = destination;
		if (source != null) {
			if (leadRequest == null) {
				leadRequest = new GenericLeadRequest();
			}
			leadRequest.setLastName(generateLeadName(source));
			leadRequest.setLeadSourceUrl(Constants.ZILLOW_LEAD_SOURCE_URL);
			leadRequest.setEmail(generateZillowHotLeadEmail(source));
			leadRequest.setSource(Constants.ZILLOW_HOTLINE_LEAD_SOURCE);
			leadRequest.setLeadType(Constants.BUYER);
			leadRequest.setPhone(source.getPhone());
			leadRequest.setState( source.getState() );
		}
		return leadRequest;
	}

	/**
	 * To generate default email for Zillow hot leads
	 * 
	 * @param source
	 * @return
	 */
	private String generateZillowHotLeadEmail(final LeadRequest source) {
		String defaultEmail = null;
		if (source.getContactId() != null) {
			defaultEmail = Constants.COMMON_LEAD_PREFIX + Constants.COMMON_EMAIL_PREFIX
					+ Constants.ZILLOW_HOTLINE_EMAIL_PREFIX + source.getContactId() + Constants.UNDER_SCORE
					+ getCurrentDateTimeSecString() + Constants.ZILLOW_HOTLEAD_DOMAIN;
		}
		return defaultEmail;
	}

	/**
	 * To get current date time string
	 * 
	 * @return
	 */
	private String getCurrentDateTimeSecString() {
		final DateFormat dateFormat = new SimpleDateFormat("ssSSSS");
		final Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * To generate default lead name
	 * 
	 * @param source
	 * @return
	 */
	private String generateLeadName(final LeadRequest source) {
		String name = null;
		if (source.getContactId() != null) {
			name = source.getContactId() + Constants.UNDER_SCORE + Constants.NOTAVAILABLE;
		}
		return name;
	}

	/**
	 * To convert LeadRequest to source
	 */
	@Override
	public LeadRequest convertFrom(final GenericLeadRequest source, final LeadRequest destination) {
		throw new UnsupportedOperationException("convertFrom operation is not supported");
	}

}
