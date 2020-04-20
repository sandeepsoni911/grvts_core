package com.owners.gravitas.business.builder;

import static com.owners.gravitas.util.UpdateValueUtil.updateField;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.util.UpdateValueUtil;

/**
 * The Class ContactBuilder.
 *
 * @author vishwanathm
 */
@Component
public class ContactBuilder extends AbstractBuilder< Contact, com.owners.gravitas.dto.Contact > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public com.owners.gravitas.dto.Contact convertTo( Contact source, com.owners.gravitas.dto.Contact destination ) {
        com.owners.gravitas.dto.Contact contact = destination;
        if (source != null) {
            if (destination == null) {
                contact = new com.owners.gravitas.dto.Contact();
            }
            BeanUtils.copyProperties( source, contact );
        }
        return contact;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public Contact convertFrom( com.owners.gravitas.dto.Contact source, Contact destination ) {
        Contact contact = destination;
        if (source != null) {
            if (destination == null) {
                contact = new Contact();
            }
            BeanUtils.copyProperties( source, contact );
        }
        return contact;
    }

    /**
     * Convert to map.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the map
     */
    public Map< String, Object > convertToMap( com.owners.gravitas.dto.Contact source, Contact destination ) {
        Contact contact = destination;
        final Map< String, Object > updatedContactMap = new HashMap<>();
        if (source != null) {
            if (destination == null) {
                contact = new Contact();
            }
            final Map< String, Object > beforeUpdateMap = contact.toAuditMap();
            BeanUtils.copyProperties( source, contact );
            final Map< String, Object > afterUpdateMap = contact.toAuditMap();
            for ( Map.Entry< String, Object > entry : afterUpdateMap.entrySet() ) {
                final Object obj = updateField( beforeUpdateMap.get( entry.getKey() ),
                        entry.getValue() );
                if (obj != null) {
                    updatedContactMap.put( entry.getKey(), obj );
                }
            }
        }
        return updatedContactMap;
    }
}
