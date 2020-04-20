/**
 *
 */
package com.owners.gravitas.business.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.LeadSyncUp;
import com.owners.gravitas.enums.LeadSyncUpAttributes;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class LeadSyncUpBuilder.
 *
 * @author kushwaja
 */
@Component
public class LeadSyncUpBuilder extends AbstractBuilder<Contact, LeadSyncUp> {

	
	/* (non-Javadoc)
	 * @see com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.Object, java.lang.Object)
	 */
	@Override
	public LeadSyncUp convertTo(final Contact source, final LeadSyncUp destination) {
		LeadSyncUp leadSyncUp = destination;
		if (source != null) {
			if (leadSyncUp == null) {
				leadSyncUp = new LeadSyncUp();
			}
			leadSyncUp.setCrmId(source.getCrmId());
			leadSyncUp.setValue(convertToJsonString(LeadSyncUpAttributes.UUID.getAttribute(),source.getOwnersComId()));
		}
		return leadSyncUp;
	}

	
	/* (non-Javadoc)
	 * @see com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Contact convertFrom(final LeadSyncUp source, final Contact destination) {
		throw new UnsupportedOperationException("convertFrom is not supported");
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
	private String convertToJsonString(final String key, final String newValue) {
		final Map<String, List<String>> jsonMap = new HashMap<String, List<String>>();
		final List<String> jsonValueList = new ArrayList<>();
		jsonValueList.add(newValue);
		jsonMap.put(key, jsonValueList);
		return JsonUtil.toJson(jsonMap);
	}
}
