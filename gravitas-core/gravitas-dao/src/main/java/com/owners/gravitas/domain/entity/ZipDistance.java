package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "GR_ZIP_DISTANCE")
public class ZipDistance extends AbstractPersistable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1499206938789404256L;

	@Column(name = "SOURCE_ZIP", nullable = false)
	private String sourceZip;

	@Column(name = "DESTINATION_ZIP", nullable = false)
	private String destinationZip;

	@Column(name = "MILES")
	private Double miles;

	@Column(name = "EXCLUDED")
	private Boolean excluded;

	public String getSourceZip() {
		return sourceZip;
	}

	public void setSourceZip(String sourceZip) {
		this.sourceZip = sourceZip;
	}

	public String getDestinationZip() {
		return destinationZip;
	}

	public void setDestinationZip(String destinationZip) {
		this.destinationZip = destinationZip;
	}

	public Double getMiles() {
		return miles;
	}

	public void setMiles(Double miles) {
		this.miles = miles;
	}

	public Boolean getExcluded() {
		return excluded;
	}

	public void setExcluded(Boolean excluded) {
		this.excluded = excluded;
	}

}
