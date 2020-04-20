package com.owners.gravitas.business.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.RecordFieldConfigEntity;
import com.owners.gravitas.dto.response.RecordViewField;

/**
 * The Class RecordViewBuilder.
 *
 * @author javeedsy
 */
@Component
public class RecordViewFieldBuilder {

	public RecordViewField fromEntity(RecordFieldConfigEntity recordFieldConfigEntity) {
		RecordViewField recordViewField = new RecordViewField();

		recordViewField.setId(recordFieldConfigEntity.getId());
		recordViewField.setColumnName(recordFieldConfigEntity.getColumnName());
		recordViewField.setDisplayName(recordFieldConfigEntity.getDisplayName());
		recordViewField.setRecordViewConfigId(recordFieldConfigEntity.getRecordViewConfigId());
		recordViewField.setDownloadEnabled(recordFieldConfigEntity.isDownloadEnabled());
		recordViewField.setMasked(recordFieldConfigEntity.isMasked());
		recordViewField.setRegexValidation(recordFieldConfigEntity.getRegexValidation());
		recordViewField.setSortable(recordFieldConfigEntity.isSortable());
		recordViewField.setVisible(recordFieldConfigEntity.isVisible());
		recordViewField.setDataType(recordFieldConfigEntity.getDataType());

		return recordViewField;
	}

	public List<RecordViewField> fromEntity(List<RecordFieldConfigEntity> recordFieldConfigEntityList) {
		List<RecordViewField> recordViewFieldList = new ArrayList<RecordViewField>();

		for (RecordFieldConfigEntity recordFieldConfigEntity : recordFieldConfigEntityList) {
			recordViewFieldList.add(fromEntity(recordFieldConfigEntity));
		}
		return recordViewFieldList;
	}

}
