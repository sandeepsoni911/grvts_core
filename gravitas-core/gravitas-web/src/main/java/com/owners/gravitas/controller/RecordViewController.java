package com.owners.gravitas.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.RecordViewBusiness;
import com.owners.gravitas.business.builder.response.RecordViewResponseBuilder;
import com.owners.gravitas.dto.RecordViewNotificationDto;
import com.owners.gravitas.dto.request.RecordViewRequest;
import com.owners.gravitas.dto.request.ReportRequest;
import com.owners.gravitas.dto.request.SavedFilterRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.RecordView;
import com.owners.gravitas.dto.response.RecordViewField;
import com.owners.gravitas.dto.response.RecordViewResponse;
import com.owners.gravitas.dto.response.SavedFilterResponse;

/**
 * Controller to expose all contracts related to record view services
 * 
 * @author sandeepsoni
 *
 */
@RestController
public class RecordViewController extends BaseWebController {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordViewController.class);

	@Autowired
	RecordViewBusiness recordViewBusiness;

	@Autowired
	RecordViewResponseBuilder recordViewResponseBuilder;
	
	/**
	 * To fetch Records based on dynamic
	 * requests 
	 * @param recordViewRequest
	 * @return
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/recordViewOld", method = RequestMethod.POST)
	public @ResponseBody RecordViewResponse exportJson(@RequestBody RecordViewRequest recordViewRequest) {
		LOGGER.info("Inside getRecordViews");
		RecordViewResponse responseJson = recordViewBusiness.getRecordView(recordViewRequest);
		return responseJson;
	}

	/**
	 * To get role based 
	 * config details
	 * @param request
	 * @return
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/role-base-config", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RecordViewResponse getRoleAndReportConfigList(
			@RequestBody @Valid final ReportRequest request) {
		return recordViewBusiness.getRoleAndReportConfigList(request);
	}

	/**
	 * To get role based
	 * access details
	 * @param roleId
	 * @return
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/role-base-record-access/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RecordViewResponse getRoleAndReportConfigList(@PathVariable final String roleId) {
		return recordViewBusiness.getRecordViewIdAndDisplayNameList(roleId);
	}

	/**
	 * To get record view
	 * config details
	 * @param request
	 * @return
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/record-view-config", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RecordViewResponse getRecordViewConfigListBasedOnRecordView(
			@RequestBody @Valid final ReportRequest request) {
		return recordViewBusiness.getRecordViewConfigListBasedOnRecordView(request);
	}

	/**
	 * To get Visible field
	 * based on record view configId
	 * @param recordViewId
	 * @return
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/record-view-visible-field/{recordViewId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RecordViewResponse getRecordViewAndItsVisibleFields(@PathVariable final String recordViewId) {
		RecordView recordView = recordViewBusiness.getRecordViewAndItsVisibleFields(recordViewId);
		recordView.setTableName(null);
		for(RecordViewField recordViewFiled : recordView.getRecordViewFieldList()) {
			recordViewFiled.setColumnName(null);
		}
		return recordViewResponseBuilder.buildResponse(recordView);
	}

	/**
	 * To get Field config details
	 * @param request
	 * @return
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/field-config", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RecordViewResponse getFieldsConfigListBasedOnRecordView(
			@RequestBody @Valid final ReportRequest request) {
		return recordViewBusiness.getFieldsConfigListBasedOnRecordView(request);
	}

	/**
	 * To get records based on
	 * recordViewConfigId and dynamic filters
	 * @param recordViewRequest
	 * @return
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/recordView", method = RequestMethod.POST)
	public @ResponseBody RecordViewResponse getRecord(@RequestBody RecordViewRequest recordViewRequest) {
		RecordViewResponse responseJson = recordViewBusiness.getRecord(recordViewRequest);
		return responseJson;
	}

	/**
	 * Gets filter by id.
	 *
	 * @param request
	 * @return saved filter response
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/filters/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public SavedFilterResponse getSavedFilter(@PathVariable final String id) {
		LOGGER.debug("Get request for saved filter!!");
		return recordViewBusiness.getSavedFilterById(id);
	}
	
	/**
	 * Gets filters by user id.
	 *
	 * @param request
	 * @return saved filter response
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/filtersByUser/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public SavedFilterResponse getSavedFiltersByUserId(@PathVariable final String userId) {
		LOGGER.debug("Get request for saved filter!!");
		return recordViewBusiness.getSavedFiltersByUserId(userId);
	}

	/**
	 * Creates the filter.
	 *
	 * @param request
	 * @return saved filter response
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/filters", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SavedFilterResponse createSavedFilter(@RequestBody @Valid final SavedFilterRequest request) {
		LOGGER.debug("New request for saved filter!!");
		return recordViewBusiness.createSavedFilter(request);
	}

	/**
	 * Saves the filter.
	 *
	 * @param request
	 * @return saved filter response
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/filters", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SavedFilterResponse updateSavedFilter(@RequestBody @Valid final SavedFilterRequest request) {
		LOGGER.debug("Update request for saved filter!!");
		return recordViewBusiness.updateSavedFilter(request);
	}
	
	
	/**
	 * Creates Notification Request.
	 *
	 * @param request
	 * @return saved filter response
	 */
	@CrossOrigin
	@PerformanceLog
	@RequestMapping(value = "/scheduleNotification", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse scheduleNotification(@RequestBody final RecordViewNotificationDto request) {
		LOGGER.debug("New request for saved filter!!");
		return recordViewBusiness.scheduleNotification(request);
	}

}
