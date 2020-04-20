package com.owners.gravitas.business.builder;

import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.RecordViewConfigEntity;
import com.owners.gravitas.dto.response.RecordView;

/**
 * The Class RecordViewBuilder.
 *
 * @author javeedsy
 */
@Component
public class RecordViewBuilder {

	public RecordView fromEntity(RecordViewConfigEntity recordViewConfigEntity) {
		RecordView recordView = new RecordView();

		recordView.setId(recordViewConfigEntity.getId());
		recordView.setTableName(recordViewConfigEntity.getTableName());
		recordView.setDisplayName(recordViewConfigEntity.getDisplayName());
		recordView.setDownloadEnabled(recordViewConfigEntity.isDownloadEnabled());
		recordView.setEnabled(recordViewConfigEntity.isEnabled());
		recordView.setScheduled(recordViewConfigEntity.isScheduled());

		return recordView;
	}

}
