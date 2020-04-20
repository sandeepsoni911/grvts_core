package com.owners.gravitas.domain;

/**
 * The Class LastViewed.
 */
public class LastViewed extends BaseDomain {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9059086946941780114L;
	/** The last viewed dtm. */
	private Long lastViewedDtm;

	/**
	 * Gets the last viewed dtm.
	 *
	 * @return the last viewed dtm
	 */
	public Long getLastViewedDtm() {
		return lastViewedDtm;
	}

	/**
	 * Sets the last viewed dtm.
	 *
	 * @param lastViewedDtm
	 *            the new last viewed dtm
	 */
	public void setLastViewedDtm(final Long lastViewedDtm) {
		this.lastViewedDtm = lastViewedDtm;
	}

}
