package com.owners.gravitas.business;

import com.owners.gravitas.dto.RecordViewNotificationDto;
import com.owners.gravitas.dto.request.RecordViewRequest;
import com.owners.gravitas.dto.request.ReportRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.RecordView;
import com.owners.gravitas.dto.request.SavedFilterRequest;
import com.owners.gravitas.dto.response.RecordViewResponse;
import com.owners.gravitas.dto.response.SavedFilterResponse;

/**
 * Interface for record view related contracts
 * 
 * @author sandeepsoni
 *
 */
public interface RecordViewBusiness {

	/**
	 * To get Records based on generic request
	 * @param recordViewRequest
	 * @return
	 */
	RecordViewResponse getRecordView(RecordViewRequest recordViewRequest);

	RecordViewResponse getRoleAndReportConfigList(ReportRequest request);

	RecordViewResponse getRecordViewIdAndDisplayNameList(String roleId);

	RecordViewResponse getRecordViewConfigListBasedOnRecordView(ReportRequest request);

	RecordViewResponse getFieldsConfigListBasedOnRecordView(ReportRequest request);
	
	
	/**
	 * To get Records based on generic request
	 * @param recordViewRequest
	 * @return
	 */
	RecordViewResponse getRecord(RecordViewRequest recordViewRequest);

	RecordView getRecordViewAndItsFields(String recordViewId);

	RecordView getRecordViewAndItsVisibleFields(String recordViewId);
	
	/**
	 * Create saved filter
	 * @param request
	 * @return
	 */
	public SavedFilterResponse createSavedFilter( SavedFilterRequest request );

	/**
	 * Update Saved Filter
	 * @param request
	 * @return
	 */
	public SavedFilterResponse updateSavedFilter( SavedFilterRequest request );

	/**
	 * Get Saved Filter By Id
	 * @param filterRequest
	 * @return
	 */
	public SavedFilterResponse getSavedFilterById(String id);

	/**
	 * To fetch saved Filters by id
	 * @param userId
	 * @return
	 */
	public SavedFilterResponse getSavedFiltersByUserId(String userId);
	
	/**
	 * To save record view notification Request
	 * @param request
	 * @return
	 */
	BaseResponse scheduleNotification(RecordViewNotificationDto request);
	

}
