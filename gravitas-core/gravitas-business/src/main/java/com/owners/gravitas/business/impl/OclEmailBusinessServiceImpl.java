package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.CRMObject.LEAD;
import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM_LOANS;
import static org.apache.commons.collections.MapUtils.isNotEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.owners.gravitas.business.AffiliateEmailBusinessService;
import com.owners.gravitas.business.BeanValidationService;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.OclEmailBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.builder.scraping.ocl.OclEmailDtoBuilder;
import com.owners.gravitas.dto.OclEmailDto;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.response.LeadResponse;
import com.owners.gravitas.exception.AffiliateEmailValidationException;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.validator.LeadValidator;

/**
 *
 * @author ankusht
 *
 *         The Class OclEmailBusinessServiceImpl.
 */
@Service
public class OclEmailBusinessServiceImpl implements OclEmailBusinessService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OclEmailBusinessServiceImpl.class );

    /** The ocl email builder. */
    @Autowired
    private OclEmailDtoBuilder oclEmailDtoBuilder;

    /** The opportunity business service. */
    @Autowired
    private OpportunityBusinessService opportunityBusinessService;

    /** The lead service. */
    @Autowired
    private LeadService leadService;

    /** instance of {@link LeadBusinessService}. */
    @Autowired
    private LeadBusinessService leadBusinessService;

    /** The affiliate email business service. */
    @Autowired
    private AffiliateEmailBusinessService affiliateEmailBusinessService;

    /** The process failed. */
    @Value( "${affiliate.mail.process.success.label}" )
    private String processingSuccessful;

    /** The lead validator. */
    @Autowired
    private LeadValidator leadValidator;

    /** The record type service. */
    @Autowired
    private RecordTypeService recordTypeService;

    /** The bean validation service. */
    @Autowired
    private BeanValidationService beanValidationService;

    /**
     * Scrape OCL email message.
     * <ul>
     * <li>If an opportunity with given loan number is found then update that
     * opportunity</li>
     * <li>Else if a lead with given email id is found then convert that lead
     * into an opportunity and then update that opportunity</li>
     * <li>Else if create a lead with given email id, convert that lead into an
     * opportunity and then update that opportunity</li>
     * </ul>
     *
     * @param message
     *            the message
     */
    @Override
    public void scrapeOclEmailMessage( final Message message ) {
        final OclEmailDto oclEmailDto = oclEmailDtoBuilder.convertToOclEmailDto( message );
        validateData( oclEmailDto, message );
        final int loanNumber = Integer.parseInt( oclEmailDto.getLoanNumber() );
        final String oppRecordTypeId = recordTypeService.getRecordTypeIdByName( OWNERS_COM_LOANS.getType(),
                OPPORTUNITY.getName() );
        String crmOpportunityId = opportunityBusinessService.getOpportunityIdByRecordTypeAndLoanNumber( oppRecordTypeId,
                loanNumber );

        if (StringUtils.isBlank( crmOpportunityId )) {
            final String leadRecordTypeId = recordTypeService.getRecordTypeIdByName( OWNERS_COM_LOANS.getType(),
                    LEAD.getName() );
            String leadId = leadBusinessService.getLeadIdByRequestTypeAndEmail( leadRecordTypeId,
                    oclEmailDto.getEmail() );
            if (StringUtils.isBlank( leadId )) {
                leadId = createOclLead( oclEmailDto, message );
            } else {
                final Map< String, Object > crmMap = new HashMap<>();
                crmMap.put( "Loan_Number__c", loanNumber );
                leadService.updateLead( crmMap, leadId );
            }

            // Once lead is found/created, convert it into an opportunity
            crmOpportunityId = leadBusinessService.convertLeadToOpportunity( leadId );
        }

        opportunityBusinessService.updateOclOpportunity( crmOpportunityId, oclEmailDto.getLoanPhase(), loanNumber,
                oppRecordTypeId );
        affiliateEmailBusinessService.addLabelToMessage( processingSuccessful, message );
    }

    /**
     * Validate data.
     *
     * @param oclEmailDto
     *            the ocl email dto
     * @param message
     *            the message
     */
    private void validateData( final OclEmailDto oclEmailDto, final Message message ) {
        final Map< String, List< String > > failedContraints = beanValidationService.validate( oclEmailDto );
        if (isNotEmpty( failedContraints )) {
            throw new AffiliateEmailValidationException( message, "Affiliate OCL email validator error",
                    failedContraints );
        }
    }

    /**
     * Creates the OCL lead.
     *
     * @param dto
     *            the dto
     * @param message
     *            the message
     * @return the string
     */
    private String createOclLead( final OclEmailDto dto, final Message message ) {
        final GenericLeadRequest genericLeadRequest = oclEmailDtoBuilder.convertTo( dto );
        leadValidator.validateLeadRequest( genericLeadRequest, message );
        final LeadResponse leadResponse = leadBusinessService.createLead( genericLeadRequest, false, null );
        final String leadId = leadResponse.getId();
        LOGGER.info( "created OCL lead successfully: " + leadId );
        return leadId;
    }

}
