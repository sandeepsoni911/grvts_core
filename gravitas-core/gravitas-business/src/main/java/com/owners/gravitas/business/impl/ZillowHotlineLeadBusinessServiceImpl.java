/**
 * 
 */
package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY;
import static java.lang.Boolean.FALSE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.ZillowHotlineLeadBusinessService;
import com.owners.gravitas.business.builder.request.ZillowHotLeadRequestBuilder;
import com.owners.gravitas.business.builder.scraping.MessageAttachmentBuilder;
import com.owners.gravitas.config.LeadBusinessConfig;
import com.owners.gravitas.config.ZillowHotlineJmxConfig;
import com.owners.gravitas.constants.CRMConstants;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.response.LeadResponse;
import com.owners.gravitas.enums.ProspectAttributeType;
import com.owners.gravitas.repository.ContactAttributeRepository;
import com.owners.gravitas.repository.ContactRepository;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.util.ZillowLeadAgentConfigUtil;

/**
 * Implementation class for ZillowHotlineLeadBusinessService
 * @author sandeepsoni
 *
 */
@Service
public class ZillowHotlineLeadBusinessServiceImpl implements ZillowHotlineLeadBusinessService {

	/** The Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZillowHotlineLeadBusinessServiceImpl.class);

	/** The Constant ZILLOW_DEDUP_BASED_ON_LAST_DIGITS. */
	private static final int ZILLOW_DEDUP_BASED_ON_LAST_DIGITS = 10;
	
	@Autowired
	ZillowHotLeadRequestBuilder zillowHotLeadRequestBuilder;

	/** instance of {@link LeadBusinessService}. */
	@Autowired
	LeadBusinessService leadBusinessService;
    
    @Autowired
    private ContactRepository contactRepository;

	@Autowired
	MessageAttachmentBuilder messageAttachmentBuilder;
	
	@Autowired
	private LeadBusinessConfig leadBusinessConfig;
	
	@Value("${zillow.hotline.lead..mail.attachmentFilename:non_pilot_crosstab}")
	private String attachmentFileName;
	
	@Value("${zillow.hotline.lead..mail.attachment.delimeter:TAB}")
	private String attachmentFileDelemeter;
	
	@Value("${zillow.hotline.lead.mail.attachment.default.charset}")
	private String attachmentFileDefaultCharset;	

    @Autowired
    private ZillowHotlineJmxConfig zillowHotlineJmxConfig;
    
    @Autowired
    private LeadService leadService;

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private ObjectTypeService objectTypeService;
    
    @Autowired
    private ContactAttributeRepository contactAttributeRepository;
    
    @Autowired
    private ObjectAttributeConfigService objectAttributeConfigService;
    
    @Autowired
    private ZillowLeadAgentConfigUtil zillowLeadAgentConfigUtil;
    
    @Autowired
    private ContactEntityService contactEntityService;
    
	/**
	 * To scrape attached CSV file for Zillow hot leads
	 */
	@Override
	public void scrapeZillowHotlineLeadAccount(final Message message) throws MessagingException, IOException {
		final List<Part> attachmentList = messageAttachmentBuilder.convertTo(message, null);
		if (CollectionUtils.isNotEmpty(attachmentList)) {
			for (final Part attachment : attachmentList) {
				LOGGER.info("checking attachments for zillow hotline lead email "+ attachment.getFileName());
				if (attachment.getFileName().startsWith(attachmentFileName)) {
					LOGGER.info("Zillow hotline lead email attachment found with fileName: " + attachment.getFileName()
							+ " : " + attachment.getContentType());
					final List<LeadRequest> zillowHotLeadRequestList = getContactIdListWithEmailAsNull(attachment,
							getCharsetFromContentType(attachment.getContentType()));
					processZillowHotLeadRequests(zillowHotLeadRequestList);
				}
			}
		}else {
			LOGGER.info("No attachment found for zillow hotline lead");
		}
	}

    /**
     * Checks if is duplicate for zillow buyer phone lead.
     *
     * @param phone
     *            the phone
     * @param agentState
     *            the agent state
     * @return true, if is duplicate for zillow buyer phone lead
     */
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    private boolean isDuplicateForZillowBuyerPhoneLead( final String phone, final String agentState ) {
        // See if the given lead is Zillow lead, if yes ignore it if the given
        // phone number is already a lead
        LOGGER.info( "Checking Zillow Duplicate Lead for Phone Number : {} ", phone );
        Contact contact = null;
        final Set< Contact > contacts = contactRepository.findTop2ByPhoneEndingWithOrderByCreatedDateDesc(
                StringUtils.right( phone, ZILLOW_DEDUP_BASED_ON_LAST_DIGITS ) );
        if (!CollectionUtils.isEmpty( contacts )) {
            contacts.stream()
                    .forEach( contactTemp -> LOGGER.info(
                            "Existing Zillow Hot Lead Phone : {}, Existing Lead Id: {}, Existing Lead CRM Id : {}",
                            phone, contactTemp.getId(), contactTemp.getCrmId() ) );
            contact = contacts.stream().findFirst().get();
            Set< ContactAttribute > contactAttributes = contact.getContactAttributes();
            if(contactAttributes == null) {
                contactAttributes = new HashSet< ContactAttribute >();
            }
            boolean isLead = true, isStateUpdated = false, isSourceUpdated = false;
            final Map< String, Object > crmMap = new HashMap< String, Object >();
            if (contact != null
                    && contact.getObjectType().getName().toLowerCase().equals( OPPORTUNITY.toLowerCase() )) {
                isLead = false;
            }
            // update state if state is null in DB or SF
			if (contact.getState() == null && agentState != null) {
                try {
                    isStateUpdated = true;
                    contact.setState( agentState );
                    if (isLead) {
                        final ObjectType objectTypeLead = objectTypeService.findByName( LEAD.toLowerCase() );
                        insertUpdateContactAttribut( contact, objectTypeLead, ProspectAttributeType.STATE.getKey(),
                                agentState );
                    } else {
                        final ObjectType objectTypeOpportunity = objectTypeService
                                .findByName( Constants.OPPORTUNITY.toLowerCase() );
                        insertUpdateContactAttribut( contact, objectTypeOpportunity,
                                ProspectAttributeType.STATE.getKey(), agentState );
                    }
                    crmMap.put( CRMConstants.STATE, agentState );
                } catch ( final Exception e ) {
                    LOGGER.info( "Exception while updating Zillow lead State", e );
                }
			}
            // updating Source
            LOGGER.error(
                    "Zillow Lead is Duplicate request for phone : {}, Existing Lead Id: {}, Existing Lead CRM Id : {}",
                    phone, contact.getId(), contact.getCrmId() );
            final String inboundCallsInsideSalesLeadSourceStr = zillowHotlineJmxConfig
                    .getInboundCallsInsideSalesLeadSourceStr();
            final String zillowLeadSourceStr = zillowHotlineJmxConfig.getZillowLeadSourceStr();
            LOGGER.info(
                    "Zillow Lead Source Correction - inboundCallsInsideSalesLeadSourceStr : {}, zillowLeadSourceStr : {}, existing contact lead source : {}",
                    inboundCallsInsideSalesLeadSourceStr, zillowLeadSourceStr, contact.getSource() );
            if (!StringUtils.isEmpty( inboundCallsInsideSalesLeadSourceStr )
                    && !StringUtils.isEmpty( contact.getSource() )) {
                // Using startsWith since in production the source is appended
                // by state / city string
                if (contact.getSource().toLowerCase()
                        .startsWith( inboundCallsInsideSalesLeadSourceStr.toLowerCase() )) {
                    /*--
                     * During email scraping of zillow lead, if we found the existing lead for given phone number, 
                     * then change its source to "Zillow Hotline" in database and also in crm saleforce. 
                     * This needs to be done so that the lead is correctly sourced to "Zillow Hotline" 
                     * which was not done correctly because the lead was earlier created via inside sales.
                     */
                    try {
                        isSourceUpdated = true;
                        contact.setSource( zillowLeadSourceStr );
                        if (isLead) {
                            final ObjectType objectTypeLead = objectTypeService.findByName( LEAD.toLowerCase() );
                            final ObjectAttributeConfig leadObjectAttributeConfig = objectAttributeConfigService
                                    .getObjectAttributeConfig( ProspectAttributeType.SOURCE.getKey(), objectTypeLead );
                            if (leadObjectAttributeConfig != null) {
                                LOGGER.info(
                                        " Zillow Lead Source Correction Criteria MATCHED Updating Values - inboundCallsInsideSalesLeadSourceStr : {}, zillowLeadSourceStr : {}, existing contact lead source : {}, contact id : {}, leadObjectAttributeConfig id : {}",
                                        inboundCallsInsideSalesLeadSourceStr, zillowLeadSourceStr, contact.getSource(),
                                        contact.getId(), leadObjectAttributeConfig.getId() );
                                contactAttributeRepository.updateContactAttribute( contact.getId(),
                                        leadObjectAttributeConfig.getId(), zillowLeadSourceStr );
                            }
                        } else {
                            final ObjectType objectTypeOpportunity = objectTypeService
                                    .findByName( Constants.OPPORTUNITY.toLowerCase() );
                            final ObjectAttributeConfig opportunityObjectAttributeConfig = objectAttributeConfigService
                                    .getObjectAttributeConfig( ProspectAttributeType.LEAD_SOURCE.getKey(),
                                            objectTypeOpportunity );
                            if (opportunityObjectAttributeConfig != null) {
                                LOGGER.info(
                                        " Zillow Opportunity Source Correction Criteria MATCHED Updating Values - inboundCallsInsideSalesLeadSourceStr : {}, zillowLeadSourceStr : {}, existing contact lead source : {}, contact id : {}, opportunityObjectAttributeConfig id : {}",
                                        inboundCallsInsideSalesLeadSourceStr, zillowLeadSourceStr, contact.getSource(),
                                        contact.getId(), opportunityObjectAttributeConfig.getId() );
                                contactAttributeRepository.updateContactAttribute( contact.getId(),
                                        opportunityObjectAttributeConfig.getId(), zillowLeadSourceStr );
                            }
                        }
                        crmMap.put( CRMConstants.LEAD_SOURCE, zillowLeadSourceStr );
                    } catch ( final Exception e ) {
                        LOGGER.error(
                                " Zillow Lead Source Correction Criteria MATCHED Exception - inboundCallsInsideSalesLeadSourceStr : {}, zillowLeadSourceStr : {}, existing contact lead source : {}",
                                inboundCallsInsideSalesLeadSourceStr, zillowLeadSourceStr, contact.getSource(), e );
                    }
                    LOGGER.error(
                            "INFO Zillow Lead Source Correction Criteria MATCHED - inboundCallsInsideSalesLeadSourceStr : {}, zillowLeadSourceStr : {}, existing contact lead source : {}",
                            inboundCallsInsideSalesLeadSourceStr, zillowLeadSourceStr, contact.getSource() );
                } else {
                    LOGGER.error(
                            "INFO Zillow Lead Source Correction Criteria NOT MATCHED - inboundCallsInsideSalesLeadSourceStr : {}, zillowLeadSourceStr : {}, existing contact lead source : {}",
                            inboundCallsInsideSalesLeadSourceStr, zillowLeadSourceStr, contact.getSource() );
                }
            } else {
                LOGGER.error(
                        "Zillow Lead Source Correction - inboundCallsInsideSalesLeadSourceStr : {}, zillowLeadSourceStr : {}, existing contact lead source : {}",
                        inboundCallsInsideSalesLeadSourceStr, zillowLeadSourceStr, contact.getSource() );
            }
            // update contact if state or source updated
            if (isStateUpdated || isSourceUpdated) {
                try {
                    final DateTime lastModifiedDate = new DateTime( System.currentTimeMillis() );
                    contact.setLastModifiedDate( lastModifiedDate );
                    contactRepository.save( contact );
                    if (isLead) {
                        leadService.updateLead( crmMap, contact.getCrmId() );
                    } else {
                        opportunityService.patchOpportunity( crmMap, contact.getCrmId() );
                    }
                } catch ( final Exception e ) {
                    LOGGER.info(
                            " Zillow Lead State/Source Correction Exception - state : {}, zillowLeadSourceStr : {}, existing contact lead source : {}",
                            agentState, zillowLeadSourceStr, contact.getSource(), e );
                }
            }
            return true;
        }
        return false;
    }

	/**
	 * To process zillow hotline lead request list
	 * 
	 * @param zillowHotLeadRequestList
	 */
	private void processZillowHotLeadRequests(final List<LeadRequest> zillowHotLeadRequestList) {
		if (CollectionUtils.isNotEmpty(zillowHotLeadRequestList)) {
			int recordNumber = 1;
			final long startTime = System.currentTimeMillis();
			LOGGER.info("Processing zillow Hotline list of size: " + zillowHotLeadRequestList.size());
			for (final LeadRequest zillowHotLeadRequest : zillowHotLeadRequestList) {
				LOGGER.info("Processing contactId for zillowHotLead : " + zillowHotLeadRequest.getContactId());
				GenericLeadRequest leadRequest = null;
				LeadResponse leadResponse = null;
				try {
					leadRequest = zillowHotLeadRequestBuilder.convertTo(zillowHotLeadRequest);
					leadResponse = leadBusinessService.createLead(leadRequest, FALSE, null);
				} catch (final Exception e) {
					LOGGER.error("Exception occurred while creating lead for zillow hotline email :"
							+ leadRequest.getEmail());
				}
				if(leadResponse != null) {
					LOGGER.info("Zillow Hotline lead created successfully with id : " + leadResponse.getId()
					 + " for recordNumber : "+ recordNumber);
				}
				recordNumber ++;
			}
			LOGGER.info("Processed zillow hotline csv file in time(seconds) : "
			+ (System.currentTimeMillis()-startTime)/1000);

		}else {
			LOGGER.info("No records qualified for zillow hot line lead creation");
		}
	}

	/**
	 * to get contactId list where email is null or empty
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<LeadRequest> getContactIdListWithEmailAsNull(final Part part, final String charset) throws IOException {
		final List<LeadRequest> zillowHotlineLeadRequestList = new ArrayList<LeadRequest>();
		BufferedReader bufferedReader = null;
		try {
		    final Set<String> fileLeadsPhoneSet = new HashSet<String>();
			final char delimeter= attachmentFileDelemeter.equals(Constants.TAB)?'\t':',';
			bufferedReader = new BufferedReader(new InputStreamReader(part.getInputStream(), charset), 64);
			LOGGER.info("Using delimeter for Zillow CSV : {}", delimeter);
			final Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().withSkipHeaderRecord(true).withDelimiter(delimeter)
					.parse(bufferedReader);
			for (final CSVRecord csvRecord : records) {
			    LOGGER.info("Parsing Zillow record: {} with size :{}", csvRecord.toString(), csvRecord.size());
                if (csvRecord.size() > 10) {
                    if (StringUtils.isBlank(csvRecord.get(4)) && StringUtils.isBlank(csvRecord.get(3))) {
                        final String phone = com.owners.gravitas.util.StringUtils.formatPhoneNumber(csvRecord.get(5).trim());
                        final String contactId = csvRecord.get(0).trim();
                        final String agentState = getAgentState( StringUtils.deleteWhitespace( csvRecord.get( 1 ) ) ) ; 
                        if (!StringUtils.isEmpty(phone) && !StringUtils.isEmpty(contactId)) {
    	                    final String createdDate = csvRecord.get(2).trim();
                            if (!fileLeadsPhoneSet.contains(phone)) {
                                fileLeadsPhoneSet.add(phone);
                                if (isValidPhone(phone) && !isDuplicateForZillowBuyerPhoneLead(phone, agentState) && isDateWithInRange(createdDate)) {
                                    final LeadRequest zillowHotLeadRequest = new LeadRequest();
                                    zillowHotLeadRequest.setContactId(contactId);
                                    zillowHotLeadRequest.setPhone( phone );
                                    zillowHotLeadRequest.setState( agentState );
                                    zillowHotlineLeadRequestList.add( zillowHotLeadRequest );
                                    LOGGER.info(
                                            "Record number is qualified for zillow Hot lead creation - record number : {}, phone number : {}, contactId : {}",
                                            csvRecord.getRecordNumber(), phone, contactId);
                                }
                            } else {
                                LOGGER.info(
                                        "Record phone number is already processed in same file, record number : {}, phone : {}, contactId : {}",
                                        csvRecord.getRecordNumber(), phone, contactId);
                            }
                        } else {
                            LOGGER.info(
                                    "Record phone number or contact id is empty, record number : {}, contactId : {}, phone : {}",
                                    csvRecord.getRecordNumber(), contactId, phone);
                        }
                    }
			    }
			}
		} catch (final Exception e) {
            LOGGER.error("Exception while parsing zillow hot lead csv file : {}", e.getMessage(), e);
		} finally {
			bufferedReader.close();
		}
		return zillowHotlineLeadRequestList;
	}
	

    /**
     * Gets the agent state.
     *
     * @param agentName
     *            the agent name
     * @return the agent state
     */
    private String getAgentState( final String agentName ) {
        if (StringUtils.isNotEmpty( agentName )) {
            return zillowLeadAgentConfigUtil.getAgentNameToStateMapping().get( agentName.toUpperCase() );
        }
        return null;
    }
	
	/**
	 * To validate phone number
	 * length
	 * @param phone
	 * @return
	 */
	private boolean isValidPhone(final String phone) {
		if (phone != null && phone.trim().length() >= 10
				&& phone.trim().length() <= 15) {
			return true;
		} else {
			LOGGER.info("lead cannot be created as phone number : {} length is invalid ", phone);
			return false;
		}

	}

	/**
	   * Parse out a charset from a content type header.
	   * 
	   * @param contentType
	   *            e.g. "text/html; charset=EUC-JP"
	   * @return "EUC-JP", or null if not found. Charset is trimmed and
	   *         uppercased.
	   */
	  private String getCharsetFromContentType(final String contentType) {
		final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");
		String charset = attachmentFileDefaultCharset;
	    if (contentType == null) {
            return charset;
        }
	    final Matcher m = charsetPattern.matcher(contentType);
		if (m.find()) {
			charset = m.group(1).trim().toUpperCase();
		}
		LOGGER.info("Zillow file charset is :" + charset);
	    return charset;
	  }
	  
	private boolean isDateWithInRange(final String createdDate) {
		final DateTime currentTime = new DateTime();
		DateTime zillowCreatedTime = null;
		if (StringUtils.isNotBlank(createdDate)) {
			zillowCreatedTime = DateUtil.toDateTime(createdDate, DateUtil.DEFAULT_OWNERS_DATE_PATTERN);
		} else {
			zillowCreatedTime = currentTime.minusHours(1);
		}
		final DateTime startTime = currentTime.minusHours(leadBusinessConfig.getZillowPhoneLeadStartHour());
		final DateTime endTime = currentTime.minusHours(leadBusinessConfig.getZillowPhoneLeadEndHour());
		if (zillowCreatedTime.isAfter(startTime) && zillowCreatedTime.isBefore(endTime)) {
			LOGGER.info("CreatedDate is in configured date range createdDate:  {} ,"
					+ " startTime : {} , endTime {}", createdDate, startTime, endTime);
			return true;
		}
		LOGGER.info("CreatedDate is not in configured date range createdDate:  {} ,"
				+ " startTime : {} , endTime {}", createdDate, startTime, endTime);
		return false;
	}
	
	/**
     * @param contact
     * @param objectType
     * @param attributreKey
     * @param attributeValue
     */
    private void insertUpdateContactAttribut( Contact contact, ObjectType objectType, String attributreKey, String attributeValue ) {
        final ObjectAttributeConfig leadObjectAttributeConfig = objectAttributeConfigService
                .getObjectAttributeConfig( attributreKey, objectType );
        if (leadObjectAttributeConfig != null) {
            LOGGER.info(
                    " Zillow Lead State update -  set from contact.state() : {} to agentState : {}, existing contact lead source : {}, contact id : {}, leadObjectAttributeConfig id : {}\",\r\n",
                    contact.getState(), attributeValue, contact.getId(), leadObjectAttributeConfig.getId() );

            ContactAttribute contactAttribute = contactAttributeRepository.getContactAttribute( contact.getId(),
                    leadObjectAttributeConfig.getId() );

            if (contactAttribute == null) {
                LOGGER.info( "for Contact id {} contactAttribute for the {} is not present, Creating new Row for it",contact.getId(),attributeValue );
                contactAttributeRepository.insertContactAttribute( UUID.randomUUID().toString(), contact.getId(),
                        leadObjectAttributeConfig.getId(), attributeValue, Constants.GRAVITAS.toString(), Constants.GRAVITAS.toString() );
                LOGGER.info( "contactAttribute new row created successfully" );
            } else {
                LOGGER.info( "for Contact id {} contactAttribute for the {} is available, updating same row",contact.getId(),attributeValue );
                contactAttribute.setValue( attributeValue );
                contactAttributeRepository.save( contactAttribute );
                LOGGER.info( "contactAttribute updated row created successfully" );
            }
        }
    }

	
}
