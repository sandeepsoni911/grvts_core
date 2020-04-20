package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static java.lang.System.currentTimeMillis;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.util.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.config.tour.TourFeedbackJmxConfig;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.Contact;

/**
 * OppAssignmentBuyerNotificationBuilder
 * 
 * @author sandeepsoni
 *
 */
@Component
public class OppAssignmentBuyerNotificationBuilder extends AbstractBuilder<OpportunitySource, EmailNotification> {
	
	 @Autowired
	 private TourFeedbackJmxConfig tourFeedbackJmxConfig;


	@Override
	public EmailNotification convertTo(OpportunitySource source, EmailNotification emailNotification) {
		if (source != null) {
			if (emailNotification == null) {
				emailNotification = new EmailNotification();
			}
			final long now = currentTimeMillis();
			emailNotification.setCreatedOn(new Timestamp(now));
			emailNotification.setClientId(NOTIFICATION_CLIENT_ID);
			emailNotification.setMessageTypeName(Constants.GRAVITAS_BUYER_AGNT_OPP_ASGMNT_NTFC);
			final Email email = new Email();
			email.setFromEmail(getAgentEmail(source.getAgentEmail()));
			final EmailRecipients recipients = new EmailRecipients();
			recipients.setToList( source.getPrimaryContact().getEmails() );
			Map< String, String > parameterMap = new HashMap< String, String >();
			parameterMap.put( "EMAIL_SUBJECT", getSubject( source.getPrimaryContact() ) );
			email.setRecipients(recipients);
			email.setSubject(getSubject( source.getPrimaryContact() ));
			email.setParameterMap( parameterMap );
			emailNotification.setEmail(email);
		}
		return emailNotification;
	}

	/**
	 * To prepare subject line
	 * 
	 * @param contact
	 * @return
	 */
	private String getSubject(Contact contact) {

		String subject = Constants.GRAVITAS_BUYER_AGNT_OPP_ASGMNT_NTFC_SUBJECT;
		if (contact != null && StringUtil.isNotBlank(contact.getFirstName())) {
			subject = contact.getFirstName() + ", " + subject;
		} else {
			subject = StringUtils.capitalizeFirstLetter(subject);
		}
		return subject;
	}

	@Override
	public OpportunitySource convertFrom(EmailNotification source, OpportunitySource destination) {
		throw new UnsupportedOperationException();
	}
	
	/**
     * To test from email as ownerstest.com
     * @param agentEmail
     * @return
     */
    private String getAgentEmail(String agentEmail) {
        // this code was added since we do not have the from domain
        // registered and hence for testing this can be used where we set the
        // from email as per our valid domain
        if (tourFeedbackJmxConfig.isUseStaticFromEmail()
                && StringUtil.isNotBlank(tourFeedbackJmxConfig.getStaticFromEmail())) {
            agentEmail = tourFeedbackJmxConfig.getStaticFromEmail();
        }
        return agentEmail;
    }
	
}
