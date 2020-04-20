package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.BLANK;
import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.enums.LeadStatus.LOST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.dao.ContactDao;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ContactJsonAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.repository.ContactRepository;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class ContactServiceImplV1.
 * 
 * @author shivamm
 *         The Class ContactServiceImplV1.
 */
@Service
public class ContactEntityServiceImplV1 implements ContactEntityService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ContactEntityServiceImplV1.class );

    /** The contact repository. */
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactDao contactDao;
    /** The object type service. */
    @Autowired
    private ObjectTypeService objectTypeService;
    /** The object attribute config service. */
    @Autowired
    private ObjectAttributeConfigService objectAttributeConfigService;

    @Override
    public void addOrUpdateAttribute( final Contact contact, final ObjectType objectType, final String key,
            final String attrValue ) {
        this.addOrUpdateJsonAttribute( contact, objectType, key, attrValue );
    }
    
    /**
     * Update contact attribute.
     *
     * @param contact
     *            the contact
     * @param objectType
     *            the object type
     * @param key
     *            the key
     * @param attrValue
     *            the attr value
     */
    @Override
    public void addOrUpdateJsonAttribute( final Contact contact, final ObjectType objectType, final String key,
            final String attrValue ) {
        String value = attrValue;
        boolean flag = false;
        if (value != null) {
            value = com.owners.gravitas.util.StringUtils.subStringForLength( value, 1000 );
            final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key,
                    objectType );
            if (config == null) {
                LOGGER.info( "Config value is  null for Object Type:{} and key :{}", objectType.getName(), key );
                return;
            }
            for ( final ContactJsonAttribute contactAttribute : contact.getContactJsonAttributes() ) {
                LOGGER.debug(
                        "for contact id :{}, contactAttribute.getObjectAttributeConfig() ::{} and checking config Value :{}",
                        contact.getId(), contactAttribute.getObjectAttributeConfig(), config );
                if (contactAttribute.getObjectAttributeConfig().equals( config )) {
                    if (contactAttribute.getValue() != null) {
                        final Map< String, List< String > > jsonMap = JsonUtil.toType( contactAttribute.getValue(),
                                Map.class );
                        final List< String > jsonValueList = jsonMap.get( key );
                        jsonValueList.add( value );
                        contactAttribute.setValue( JsonUtil.toJson( jsonMap ) );
                    } else {
                        convertToJsonString( key, value );
                        contactAttribute.setValue( value );
                        flag = true;
                        break;
                    }
                }
                contact.getContactJsonAttributes().add( contactAttribute );
            }
            if (!flag) {
                final ContactJsonAttribute contactAttribute = new ContactJsonAttribute();
                contactAttribute.setValue( convertToJsonString( key, value ) );
                contactAttribute.setObjectAttributeConfig( config );
                contact.getContactJsonAttributes().add( contactAttribute );
            }
        }
    }

    /**
     * Gets the json string.
     *
     * @param key
     *            the key
     * @param newValue
     *            the new value
     * @return the json string
     */
    private String convertToJsonString( final String key, final String newValue ) {
        final Map< String, List< String > > jsonMap = new HashMap< String, List< String > >();
        final List< String > jsonValueList = new ArrayList<>();
        jsonValueList.add( newValue );
        jsonMap.put( key, jsonValueList );
        return JsonUtil.toJson( jsonMap );
    }

    @Override
    public void addContactAttribute( final Set< ContactAttribute > attributes, final String key, final String value,
            final ObjectType objectType ) {
        boolean flag = false;
        String currentValue = value;
        if (currentValue != null) {
            currentValue = com.owners.gravitas.util.StringUtils.subStringForLength( currentValue, 1000 );
            final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key,
                    objectType );
            for ( final ContactAttribute contactAttribute : attributes ) {
                if (contactAttribute.getObjectAttributeConfig().equals( config )) {
                    contactAttribute.setValue( currentValue );
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                final ContactAttribute contactAttribute = new ContactAttribute();
                contactAttribute.setValue( currentValue );
                contactAttribute.setObjectAttributeConfig( config );
                attributes.add( contactAttribute );
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#save(com.owners.gravitas.
     * domain.entity.Contact)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public com.owners.gravitas.domain.entity.Contact save( final com.owners.gravitas.domain.entity.Contact contact ) {
        return contactRepository.save( contact );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#save(com.owners.gravitas.
     * domain.entity.Contact)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public List<Contact> save( final List<com.owners.gravitas.domain.entity.Contact> contacts ) {
        return contactRepository.save( contacts );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.ContactServiceV1#findByCrmId(java.lang.
     * String)
     */
    @Override
    public Contact findByCrmId( final String crmId ) {
        return contactRepository.findByCrmId( crmId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#findByEmailAndType(java.lang
     * .String, java.lang.String)
     */
    @Override
    public Contact getContact( final String email, final String recordType ) {
        Contact retVal = null;
        final List< Contact > contacts = contactRepository.findByEmailAndTypeAndStageNotOrderByCreatedDateAsc( email,
                recordType, LOST.getStatus() );
        if (CollectionUtils.isNotEmpty( contacts )) {
            retVal = contacts.get( 0 );
            for ( final Contact c : contacts ) {
                if (StringUtils.isNotBlank( c.getOwnersComId() )) {
                    retVal = c;
                    break;
                }
            }
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#getContactByCrmId(java.lang.
     * String)
     */
    @Override
    public Contact getContactByCrmId( final String crmId ) {
        return contactRepository.findByCrmId( crmId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#getRevenueOfAgents(java.lang
     * .String, java.lang.String, int)
     */
    @Override
    public List< Object[] > getRevenueOfAgents( final String fromDate, final String toDate, final int rankFilter ) {
        return contactRepository.getRevenueByAgents( fromDate, toDate, rankFilter );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#getRevenueOfAgent(java.lang.
     * String, java.lang.String, java.lang.String)
     */
    @Override
    public List< Object[] > getRevenueOfAgent( final String agentEmail, final String fromDate, final String toDate ) {
        return contactRepository.getRevenueByAgent( agentEmail, fromDate, toDate );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#getRevenueOfAgents(java.lang
     * .String, java.lang.String)
     */
    @Override
    public List< Object[] > getRevenueOfAgents( final String fromDate, final String toDate ) {
        return contactRepository.getRevenueByAgents( fromDate, toDate );
    }

    /**
     * Gets the contact by owners com id.
     *
     * @param ownersComId
     *            the owners com id
     * @return the contact by owners com id
     */
    @Override
    public Contact getContactByOwnersComId( final String ownersComId ) {
        final Set< Contact > contacts = contactRepository.findByOwnersComId( ownersComId );
        LOGGER.info( " number of contact found : " + contacts.size() + " for contat id: " + ownersComId );
        return contacts.size() > 0 ? contacts.iterator().next() : null;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#getOutboundAttemptContacts(
     * java.lang.String, java.lang.String)
     */
    @Override
    public List< Contact > getOutboundAttemptContacts( final String stage, final String objectType ) {
        return contactRepository.getContactByStageAndObjectType( stage, objectType );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#getContactByEmail(java.lang.
     * String)
     */
    @Override
    public Contact getContactByEmail( final String email ) {
        final Set< Contact > contacts = contactRepository.findByEmail( email );
        LOGGER.info( " number of contact found : " + contacts.size() + " for email :" + email );
        return contacts.size() > 0 ? contacts.iterator().next() : null;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#getContactByOpportunities(
     * java.util.Set)
     */
    @Override
    public Contact getContactByOpportunities( final Set< Opportunity > opportunities ) {
        return contactRepository.findByOpportunitiesIn( opportunities );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#getContactByEmailAndType(
     * java.lang.String, java.lang.String)
     */
    @Override
    public Contact getContactByEmailAndType( final String email, final String type ) {
        return contactRepository.findByEmailAndType( email, type );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactServiceV1#findSaveSearchStatus(java.
     * lang.String)
     */
    @Override
    public List< Object[] > findSaveSearchStatus( final String contactId ) {
        return contactDao.findSaveSearchStatus( contactId );
    }

    @Override
    public Contact getContactByFbOpportunityId( final String fbOpportunityId ) {
        return contactRepository.getContactByFbOpportunityId( fbOpportunityId );
    }

    @Override
    public List< Contact > getAllContactByFbOpportunityId( final Set< String > fbOpportunityIds ) {
        return contactRepository.getAllContactByFbOpportunityId( fbOpportunityIds );
    }

    @Override
    public List< Object[] > findAllContactsByFbOpportunityId( final Set< String > fbOpportunityId ) {
        return contactDao.findAllContactsByFbOpportunityId( fbOpportunityId );
    }

    @Override
    public String getUserName( final Contact contact ) {
        String name = BLANK;
        if (null != contact) {
            name = ( null != contact.getFirstName() ? ( contact.getFirstName() + BLANK_SPACE ) : BLANK )
                    + ( null != contact.getLastName() ? contact.getLastName() : BLANK );
        }
        return name.trim();
    }

    /**
     * Gets the contact attribute.
     *
     * @param contactAttributeSet
     *            the contact attribute set
     * @param attributeName
     *            the attribute name
     * @return the contact attribute
     */
    @Override
    public String getContactAttribute( final Set< ContactAttribute > contactAttributeSet, final String attributeName ) {
        String attributeValue = null;
        if (!CollectionUtils.isEmpty( contactAttributeSet )) {
            for ( final ContactAttribute contactAttribute : contactAttributeSet ) {
                if (contactAttribute.getObjectAttributeConfig().getAttributeName().equalsIgnoreCase( attributeName )) {
                    attributeValue = contactAttribute.getValue();
                    break;
                }
            }
        }
        return attributeValue;
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.ContactServiceV1#getAllPublicLeads(int,
     * int, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public Page< Contact > getAllPublicLeads( final int page, final int size, final String direction,
            final String property ) {
        final String[] properties = property.split( "," );
        Pageable pageable = null;
        Page< Contact > response = null;
        if (property.contains( "buyerLeadScore" )) {
            pageable = new PageRequest( page, size );
            if ("asc".equalsIgnoreCase( direction )) {
                response = contactRepository.findAllPublicLeadsByScoreAsc( pageable );
            } else {
                response = contactRepository.findAllPublicLeadsByScoreDesc( pageable );
            }
        } else if (property.contains( "leadName" )) {
            pageable = new PageRequest( page, size );
            if ("asc".equalsIgnoreCase( direction )) {
                response = contactRepository.findAllPublicLeadsByLeadNameAsc( pageable );
            } else {
                response = contactRepository.findAllPublicLeadsByLeadNameDesc( pageable );
            }
        } else {
            pageable = new PageRequest( page, size, Direction.fromStringOrNull( direction ), properties );
            response = contactRepository.findAllPublicLeads( pageable );
        }
        return response;
    }

	@Override
	public Contact getCrmIdAndDeletedIsFalse(final String crmId, final String type) {
		final List<Contact> contactList = contactRepository.getCrmIdAndDeletedIsFalse(crmId, type);
		if(contactList != null && contactList.size() > 0){
			return contactList.get(0);
		}
		return null;
	}
	
	@Override
    public Contact getOpportunityByCrmId(final String crmId) {
        final List<Contact> contactList = contactRepository.getOpportunityByCrmId(crmId);
        if(contactList != null && contactList.size() > 0){
            return contactList.get(0);
        }
        return null;
    }
	
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.ContactServiceV1#getAllMyLeads(int, int,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public Page< Contact > getAllMyLeads( final int page, final int size, final String direction, final String property,
            final String emailId, String type ) {
        final String[] properties = property.split( "," );
        Pageable pageable = null;
        Page< Contact > response = null;
        if (property.contains( "buyerLeadScore" )) {
            pageable = new PageRequest( page, size );
            if ("asc".equalsIgnoreCase( direction )) {
                response = contactRepository.findAllMyLeadsByScoreAsc( pageable, emailId, type );
            } else {
                response = contactRepository.findAllMyLeadsByScoreDesc( pageable, emailId, type );
            }
        } else if (property.contains( "leadName" )) {
            pageable = new PageRequest( page, size );
            if ("asc".equalsIgnoreCase( direction )) {
                response = contactRepository.findAllMyLeadsByLeadNameAsc( pageable, emailId, type );
            } else {
                response = contactRepository.findAllMyLeadsByLeadNameDesc( pageable, emailId, type );
            }
        } else {
            pageable = new PageRequest( page, size, Direction.fromStringOrNull( direction ), properties );
            response = contactRepository.findAllMyLeads( pageable, emailId, type );
        }
        return response;
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.ContactEntityService#getContactAttributesForCrmId(java.util.Set)
     */
    @Override
    @Transactional( readOnly = true )
    public List<Object[]> getLeadAttributesForCrmId(final Set<String> crmIdList) {
        return contactRepository.getContactAttributesDataForCrmId( crmIdList );
    }
    
    @Override
    @Transactional( readOnly = true )
    public List<Object[]> getOpportunityAttributesForCrmId(final Set<String> crmIdList) {
        return contactRepository.getOpportunityContactAttributesDataForCrmId( crmIdList );
    }
    
    /**
     * Gets the contact attribute.
     *
     * @param contact
     *            the contact
     * @param key
     *            the key
     * @param objectTypeValue
     *            the object type value
     * @return the contact attribute
     */
    @Override
    public String getContactAttribute( final Contact contact, final String key, final String objectTypeValue ) {
        String attrValue = "";
        final ObjectType objectType = objectTypeService.findByName( objectTypeValue );
        final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key, objectType );
        for ( final ContactAttribute contactAttribute : contact.getContactAttributes() ) {
            if (contactAttribute.getObjectAttributeConfig().equals( config )) {
                attrValue = contactAttribute.getValue();
                break;
            }
        }
        return attrValue;
    }
    
    /* (non-Javadoc)
	 * @see com.owners.gravitas.service.ContactEntityService#findLeadsByOwnersComIdIsNull(int, int, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Contact> findLeadsByOwnersComIdIsNull(String fromDate, String toDate) {
		//Pageable pageable = new PageRequest( pageNo, size );
		List< Contact > response = contactRepository.findLeadsByOwnersComIdIsNull(fromDate, toDate);
		return response;
	}

	/* (non-Javadoc)
	 * @see com.owners.gravitas.service.ContactEntityService#findAllLeadsByOwnersComIdIsNull(int, int)
	 */
	@Override
	public List<Contact> findAllLeadsByOwnersComIdIsNull() {
		//Pageable pageable = new PageRequest( pageNo, size );
		List< Contact > response = contactRepository.findAllLeadsByOwnersComIdIsNull();
		return response;
	}
}
