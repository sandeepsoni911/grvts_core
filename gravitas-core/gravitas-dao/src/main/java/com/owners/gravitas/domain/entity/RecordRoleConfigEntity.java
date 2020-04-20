package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class RecordRoleConfigEntity.
 *
 * @author javeedsy
 */
@Entity(name = "GR_RECORD_ROLE_CONFIG")
public class RecordRoleConfigEntity extends AbstractAuditable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1633548100973832562L;

	@Column(name = "REC_VIEW_CONFIG_ID", nullable = false)
	private String recordViewConfigId;

	@Column(name = "ROLE_ID", nullable = false)
	private String roleId;

	/**
	 * @return the recordViewConfigId
	 */
	public String getRecordViewConfigId() {
		return recordViewConfigId;
	}

	/**
	 * @param recordViewConfigId
	 *            the recordViewConfigId to set
	 */
	public void setRecordViewConfigId(String recordViewConfigId) {
		this.recordViewConfigId = recordViewConfigId;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
