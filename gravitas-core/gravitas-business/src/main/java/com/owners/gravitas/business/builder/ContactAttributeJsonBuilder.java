package com.owners.gravitas.business.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.ContactJsonAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class ContactAttributeJsonBuilder.
 *
 * @author shivamm
 */
@Component( "contactAttributeJsonBuilder" )
public class ContactAttributeJsonBuilder extends AbstractBuilder< Set< ContactJsonAttribute >, LeadRequest > {

    /** The object type service. */
    @Autowired
    private ObjectTypeService objectTypeService;

    /** The object attribute config service. */
    @Autowired
    private ObjectAttributeConfigService objectAttributeConfigService;

    /**
     * Convert from.
     *
     * @param source
     *            the source
     * @param crmLeadResponse
     *            the crm lead response
     * @return the sets the
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    public Set< ContactJsonAttribute > convertFrom( final LeadRequest source,
            Set< ContactJsonAttribute > destination ) {
        Set< ContactJsonAttribute > contactJsonAttribute = destination;
        if (source != null) {
            if (contactJsonAttribute == null) {
                contactJsonAttribute = new HashSet< ContactJsonAttribute >();
            }
            ObjectType objectType = objectTypeService.findByName( "lead" );
            setContactJsonAttributes( contactJsonAttribute, source, objectType );
        }
        return contactJsonAttribute;
    }

    /**
     * Sets the contact json attributes.
     *
     * @param contactJsonAttribute
     *            the contact json attribute
     * @param source
     *            the source
     * @param crmLeadResponse
     *            the crm lead response
     * @param objectType
     *            the object type
     */
    private void setContactJsonAttributes( Set< ContactJsonAttribute > contactJsonAttribute, final LeadRequest source,
            final ObjectType objectType ) {
        addIfNotNullContactJsonAttributes( contactJsonAttribute, "savedSearchValues", source.getSavedSearchValues(),
                objectType );
        addIfNotNullContactJsonAttributes( contactJsonAttribute, "propertyTourInformation",
                source.getPropertyTourInformation(), objectType );
        addIfNotNullContactJsonAttributes( contactJsonAttribute, "additionalPropertyData",
                source.getAdditionalPropertyData(), objectType );
        addIfNotNullContactJsonAttributes( contactJsonAttribute, "websiteSessionData", source.getWebsiteSessionData(),
                objectType );
        addIfNotNullContactJsonAttributes( contactJsonAttribute, "orderId", source.getOrderId(), objectType );
    }

    /**
     * Gets the json string.
     *
     * @param key
     *            the key
     * @param oldValue
     *            the old value
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

    /**
     * Adds the if not null.
     *
     * @param attributes
     *            the attributes
     * @param key
     *            the key
     * @param value
     *            the values
     * @param objectType
     *            the object type
     */
    private void addIfNotNullContactJsonAttributes( final Set< ContactJsonAttribute > attributes, final String key,
            String value, final ObjectType objectType ) {

        boolean flag = false;
        if (value != null) {
            if (value.length() > 1000) {
                value = value.substring( 0, 1000 );
            }
            ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key,
                    objectType );
            for ( ContactJsonAttribute contactAttribute : attributes ) {
                if (contactAttribute.getObjectAttributeConfig().equals( config )) {
                    if (contactAttribute.getValue() != null) {
                        final Map< String, List< String > > jsonMap = JsonUtil.toType( contactAttribute.getValue(), Map.class );
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
            }
            if (!flag) {
                final ContactJsonAttribute contactAttribute = new ContactJsonAttribute();
                contactAttribute.setValue( convertToJsonString( key, value ) );
                contactAttribute.setObjectAttributeConfig(config);
                attributes.add( contactAttribute );
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public LeadRequest convertTo( Set< ContactJsonAttribute > source, LeadRequest destination ) {
        throw new UnsupportedOperationException( "convertTo is not supported" );
    }
}
