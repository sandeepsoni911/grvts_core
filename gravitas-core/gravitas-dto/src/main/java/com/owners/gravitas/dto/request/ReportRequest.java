package com.owners.gravitas.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ReportRequest.
 *
 * @author javeedsy
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportRequest {

	@NotNull(message = "List can not be null or empty or blank")
	@Size(min = 1, max = 100)
	private List<String> ids;

	/**
	 * @return the ids
	 */
	public List<String> getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}


}
