package com.owners.gravitas.business.handler;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM_LOANS;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.entity.ContactStatus;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.repository.ContactStatusRepository;

@Component("leadProcessingRetryHandler")
public class LeadProcessingRetryHandler 
{
	/** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadProcessingRetryHandler.class );
    
    @Autowired
    ContactStatusRepository contactStatusRepository;
    
    @Autowired
    private LeadBusinessService leadBusinessService;
    
    @Value("${failed.lead.max.retry.count}")
	private int retryCount;
    
    /**
     * Below primary method called from the controller / scheduler which would retry processing the failed leads
     */
    @Transactional
    public String retryFailedLeads()
    {
    	LOGGER.debug("retryCount : "+retryCount);
    	List<ContactStatus> contactStatusList = contactStatusRepository.getContactStatusUnderRetryCount(retryCount);

    	if(contactStatusList != null && contactStatusList.size() > 0)
    	{
    		LOGGER.debug("Failed lead count : "+contactStatusList.size());
    		List<String> idList = new ArrayList<String>();

    		for(ContactStatus contactStatus : contactStatusList)
    		{
    			try
    			{
    				updateDbRetryCount( contactStatus );
    				GenericLeadRequest lr = convertToLeadRequest(contactStatus.getRequestJson(), contactStatus.getLeadSource());
    				LOGGER.debug("Converted obj : "+lr);
    				if(!lr.getLeadType().equalsIgnoreCase( OWNERS_COM_LOANS.getType() ))
    				{
    					leadBusinessService.createLead( lr, Boolean.TRUE, contactStatus.getId() );
    				}
    				else
    				{
    					leadBusinessService.createOclLead( lr, contactStatus.getId() );
    				}

    				idList.add(contactStatus.getId());
    				updateDbRetryStatus(contactStatus);
    			}
    			catch(Exception e)
    			{
    				LOGGER.error("Error in processing the failed lead : "+contactStatus.getId()+" ",e);
    			}
    		}
    		return idList.toString();
    	}


    	return null;
    }
    
    private GenericLeadRequest convertToLeadRequest(String request, String leadSource)
    {
    	try
    	{
    		ObjectMapper objMapper = new ObjectMapper();
    		final GenericLeadRequest leadreq = objMapper.readValue(request, GenericLeadRequest.class);
    		leadreq.setLeadType(leadreq.getLeadType().toUpperCase());
    		return leadreq;
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Exception in parsing the jSon string request : ",e);
    	}
    	return null;
    }
    
    private void updateDbRetryCount( final ContactStatus contactStatus )
    {
    	contactStatus.setRetryCount(contactStatus.getRetryCount()+1);
		contactStatusRepository.save(contactStatus);
    }
    
    private void updateDbRetryStatus( final ContactStatus contactStatus )
    {
    	contactStatus.setStatus(Constants.SUCCESS);
		contactStatusRepository.save(contactStatus);
    }

}