package com.owners.gravitas.business.builder;
/**
 * Builder to build RecordViewScheduler Request
 * @author sandeepsoni
 *
 */

import org.springframework.stereotype.Component;
import com.owners.gravitas.domain.entity.RecordReportsNotficationEntity;
import com.owners.gravitas.dto.RecordViewNotificationDto;

@Component
public class RecordViewSchedulerBuilder {

	/**
	 * Converts Entity to DTO object
	 * @param entity
	 * @return
	 */
	public RecordViewNotificationDto fromEntity(RecordReportsNotficationEntity entity) {
		RecordViewNotificationDto recordViewNotificationDto = null;
		if(entity != null) {
			recordViewNotificationDto = new RecordViewNotificationDto();
			recordViewNotificationDto.setFileType(entity.getFileType());
			recordViewNotificationDto.setReoccuring(entity.isReoccuring());
			recordViewNotificationDto.setSavedFilterId(entity.getSavedFilterId());
			recordViewNotificationDto.setStatus(entity.getStatus());
			recordViewNotificationDto.setTriggerTime(entity.getTriggerTime());
			recordViewNotificationDto.setFilePath(entity.getFilePath());
		}
		
		return recordViewNotificationDto;
		
	}
	
	/**
	 * Converts DTO to entity object
	 * @param dto
	 * @return
	 */
	public RecordReportsNotficationEntity toEntity(RecordViewNotificationDto dto) {
		RecordReportsNotficationEntity entity = null;
		if(dto != null) {
			entity = new RecordReportsNotficationEntity();
			entity.setFileType(dto.getFileType());
			entity.setReoccuring(dto.isReoccuring());
			entity.setSavedFilterId(dto.getSavedFilterId());
			entity.setStatus(dto.getStatus());
			entity.setTriggerTime(dto.getTriggerTime());
			entity.setFilePath(dto.getFilePath());
			entity.setCreatedBy("APPLICATION");
		}
		return entity;
	}
}
