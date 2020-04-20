package com.owners.gravitas.enums;

/**
 * The Enum LeadSyncUpAttributes.
 *
 * @author kushwaja
 */
public enum LeadSyncUpAttributes {

	/** The uuid. */
	UUID("uuid");

	/** The status. */
	private String attribute;

	/**
	 * Instantiates a new lead getAttribute.
	 *
	 * @param getAttribute
	 *            the getAttribute
	 */
	private LeadSyncUpAttributes(final String attribute) {
		this.attribute = attribute;
	}

	/**
	 * Gets the attribute.
	 *
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * Gets the lead attribute.
	 *
	 * @param attribute
	 *            the attribute
	 * @return the lead attribute
	 */
	public static LeadSyncUpAttributes getType(final String attribute) {
		for (final LeadSyncUpAttributes value : values()) {
			if (value.getAttribute().equalsIgnoreCase(attribute)) {
				return value;
			}
		}
		return UUID;
	}
}
