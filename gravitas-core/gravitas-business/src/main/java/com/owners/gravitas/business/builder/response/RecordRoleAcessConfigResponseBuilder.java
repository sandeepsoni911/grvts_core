package com.owners.gravitas.business.builder.response;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.RecordRoleConfigEntity;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.RecordViewResponse;

/**
 * The class RecordRoleAcessConfigResponseBuilder
 * 
 * @author javeedsy
 *
 */
@Component
public class RecordRoleAcessConfigResponseBuilder {

	public RecordViewResponse buildResponse(List<RecordRoleConfigEntity> resultList) {
		RecordViewResponse response = new RecordViewResponse();
		if (CollectionUtils.isNotEmpty(resultList)) {
			response.setConfigData(resultList);
			response.setStatus(Status.SUCCESS);
		} else {
			response.setStatus(Status.SUCCESS);
			response.setMessage("No records found for the input.");
		}
		return response;
	}

}
