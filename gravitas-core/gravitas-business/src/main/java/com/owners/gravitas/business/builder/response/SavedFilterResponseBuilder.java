package com.owners.gravitas.business.builder.response;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.RecordViewSchedulerBuilder;
import com.owners.gravitas.domain.entity.RecordReportsNotficationEntity;
import com.owners.gravitas.domain.entity.RecordViewSavedFilterEntity;
import com.owners.gravitas.dto.RecordViewNotificationDto;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.SavedFilter;
import com.owners.gravitas.dto.response.SavedFilterResponse;
import com.owners.gravitas.service.RecordViewService;

/**
 * The class SavedFilterResponseBuilder
 * 
 * @author chinmaytotekar
 *
 */
@Component
public class SavedFilterResponseBuilder {
	
	@Autowired
	RecordViewService recordViewService;
	
	@Autowired
	RecordViewSchedulerBuilder recordViewSchedulerBuilder;

	public SavedFilterResponse buildResponse(List<RecordViewSavedFilterEntity> filters) {
		SavedFilterResponse response = new SavedFilterResponse();
		if (CollectionUtils.isNotEmpty(filters)) {
			List<com.owners.gravitas.dto.response.SavedFilter> results = new ArrayList<com.owners.gravitas.dto.response.SavedFilter>();
			for (RecordViewSavedFilterEntity savedFilter : filters) {
				com.owners.gravitas.dto.response.SavedFilter filter = new com.owners.gravitas.dto.response.SavedFilter();
				BeanUtils.copyProperties(savedFilter, filter);
				List<RecordReportsNotficationEntity> notificationEntityList = recordViewService.getBySavedSearchId(savedFilter.getId());
				if(CollectionUtils.isNotEmpty(notificationEntityList)) {
					List<RecordViewNotificationDto> notificationDetailList = null;
					for(RecordReportsNotficationEntity entity :notificationEntityList ) {
						notificationDetailList = new ArrayList<RecordViewNotificationDto>();
						RecordViewNotificationDto notifiationDetails = recordViewSchedulerBuilder.fromEntity(entity);
						notificationDetailList.add(notifiationDetails);
					}
					filter.setNotficationDetails(notificationDetailList);
				}
				results.add(filter);
			}
			response.setData(results);
			response.setStatus(Status.SUCCESS);
		} else {
			response.setStatus(Status.SUCCESS);
			response.setMessage("No records found for the input.");
		}
		return response;
	}

	/**
	 * To format DateTime
	 * @param triggerTime
	 * @return
	 */
	private String formatDateTime(Timestamp triggerTime) {
		String formattedDateTime = null;
		if(triggerTime != null) {
			SimpleDateFormat simpleDate = new SimpleDateFormat();
			formattedDateTime = simpleDate.format(triggerTime);
		}
		return formattedDateTime;
	}

}
