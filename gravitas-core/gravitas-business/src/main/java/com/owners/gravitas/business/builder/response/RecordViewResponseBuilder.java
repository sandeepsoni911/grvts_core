package com.owners.gravitas.business.builder.response;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.RecordView;
import com.owners.gravitas.dto.response.RecordViewResponse;

/**
 * The class RecordViewResponseBuilder
 * @author sandeepsoni
 *
 */
@Component
public class RecordViewResponseBuilder {
	
	public RecordViewResponse buildResponse(List<Map<String, Object>> recordViewResult, Integer totalCount) {
		RecordViewResponse response = new RecordViewResponse();
		if(CollectionUtils.isNotEmpty(recordViewResult) ) {
			response.setData(recordViewResult);
			response.setTotalCount(totalCount);
			response.setStatus(Status.SUCCESS);
		}else if(totalCount != null && totalCount == 0){
			response.setStatus(Status.SUCCESS);
			response.setMessage("No Records Found.");
		}else {
			response.setStatus(Status.FAILURE);
		}
		return response;		
	}
	
	public RecordViewResponse buildResponse(RecordView recordView) {
		RecordViewResponse response = new RecordViewResponse();
		if(recordView  != null) {
			response.setConfigData(recordView);
			response.setStatus(Status.SUCCESS);
		}else {
			response.setStatus(Status.SUCCESS);
			response.setMessage("No records found for the input.");
		}
		return response;		
	}

}
