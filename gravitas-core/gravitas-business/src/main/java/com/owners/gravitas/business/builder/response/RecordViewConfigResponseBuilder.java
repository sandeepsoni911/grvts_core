package com.owners.gravitas.business.builder.response;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.RecordViewConfigEntity;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.RecordViewResponse;

/**
 * The class RecordViewConfigResponseBuilder
 * 
 * @author javeedsy
 *
 */
@Component
public class RecordViewConfigResponseBuilder {

	public RecordViewResponse buildResponse(List<RecordViewConfigEntity> resultList) {
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

	public RecordViewResponse buildListOfMapResponse(List<Map<String, Object>> recordViewResult) {
		RecordViewResponse response = new RecordViewResponse();
		if (CollectionUtils.isNotEmpty(recordViewResult)) {
			response.setData(recordViewResult);
			response.setStatus(Status.SUCCESS);
		} else {
			response.setStatus(Status.SUCCESS);
			response.setMessage("No records found for the input.");
		}
		return response;
	}

}
