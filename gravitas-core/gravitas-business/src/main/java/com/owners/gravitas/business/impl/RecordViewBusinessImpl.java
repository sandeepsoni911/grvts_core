package com.owners.gravitas.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.business.RecordViewBusiness;
import com.owners.gravitas.business.builder.RecordViewBuilder;
import com.owners.gravitas.business.builder.RecordViewFieldBuilder;
import com.owners.gravitas.business.builder.RecordViewSchedulerBuilder;
import com.owners.gravitas.business.builder.response.RecordFieldConfigResponseBuilder;
import com.owners.gravitas.business.builder.response.RecordRoleAcessConfigResponseBuilder;
import com.owners.gravitas.business.builder.response.RecordViewConfigResponseBuilder;
import com.owners.gravitas.business.builder.response.RecordViewResponseBuilder;
import com.owners.gravitas.business.builder.response.SavedFilterResponseBuilder;
import com.owners.gravitas.domain.entity.RecordReportsNotficationEntity;
import com.owners.gravitas.domain.entity.RecordRoleConfigEntity;
import com.owners.gravitas.domain.entity.RecordViewSavedFilterEntity;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.dto.RecordViewNotificationDto;
import com.owners.gravitas.dto.request.ConditionGroup;
import com.owners.gravitas.dto.request.RecordViewRequest;
import com.owners.gravitas.dto.request.ReportRequest;
import com.owners.gravitas.dto.request.SavedFilterRequest;
import com.owners.gravitas.dto.request.WhereClause;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.RecordView;
import com.owners.gravitas.dto.response.RecordViewField;
import com.owners.gravitas.dto.response.RecordViewResponse;
import com.owners.gravitas.dto.response.SavedFilterResponse;
import com.owners.gravitas.service.RecordViewService;
import com.owners.gravitas.service.SavedFilterService;
import com.owners.gravitas.service.UserService;



/**
 * Implementation class for RecordViewBusiness
 * 
 * @author sandeepsoni
 *
 */
@Service("recordViewBusinessImpl")
public class RecordViewBusinessImpl implements RecordViewBusiness {

	@Autowired
	RecordViewService recordViewService;

	@Autowired
	RecordViewResponseBuilder recordViewResponseBuilder;

	@Autowired
	RecordRoleAcessConfigResponseBuilder recordRoleAcessConfigResponseBuilder;

	@Autowired
	RecordViewConfigResponseBuilder recordViewConfigResponseBuilder;

	@Autowired
	RecordFieldConfigResponseBuilder recordFieldConfigResponseBuilder;
	
	@Autowired
	SavedFilterResponseBuilder savedFilterResponseBuilder;

	@Autowired
	RecordViewBuilder recordViewBuilder;

	@Autowired
	RecordViewFieldBuilder recordViewFieldBuilder;
	
	@Autowired
	SavedFilterService savedFilterService;
	
	@Autowired
	RecordViewSchedulerBuilder recordViewSchedulerBuilder;
	
	@Autowired
	UserService userService;
	

	@Override
	public RecordViewResponse getRecordView(RecordViewRequest recordViewRequest) {
		RecordViewResponse recordViewResponse = new RecordViewResponse();

		if (recordViewRequest == null || StringUtils.isEmpty(recordViewRequest.getRecordViewConfigId())) {
			recordViewResponse.setStatus(Status.FAILURE);
			recordViewResponse.setMessage("Invalid request");
			return recordViewResponse;
		}
		if (validateAccess(recordViewRequest.getRecordViewConfigId(), recordViewRequest.getRoleList())) {
			RecordView recordView = getRecordViewAndItsVisibleFields(recordViewRequest.getRecordViewConfigId());
			recordViewRequest.setTableName(recordView.getTableName());
			String conditions = createDynamicWhereClause(recordViewRequest, recordView.getRecordViewFieldList());
			recordViewRequest.setColumnName(recordView.getCommaSeperatedVisibleColumnList());
			String recordViewQuery = prepareNativeQuery(recordViewRequest, conditions);

			List<Map<String, Object>> response = recordViewService.getRecordView(recordViewQuery);
			recordViewResponse = recordViewResponseBuilder.buildResponse(response, 0);
		} else {
			recordViewResponse.setStatus(Status.FAILURE);
			recordViewResponse.setMessage("You are not authorized to view this report.");
		}
		return recordViewResponse;
	}
	
	/**
	 * To generate where clause dynamically
	 * @return
	 */
	private String createDynamicWhereClause(RecordViewRequest recordViewRequest, List<RecordViewField> recordViewFieldList) {
		StringBuilder whereClause = new StringBuilder("");
		if(recordViewRequest == null || CollectionUtils.isEmpty(recordViewFieldList) 
				||  CollectionUtils.isEmpty(recordViewRequest.getConditionGroup())) {
			return whereClause.toString();
		}
		for(ConditionGroup group : recordViewRequest.getConditionGroup()) {
			whereClause.append(" ( ").append(getColumnNamesInWhereCondtion(recordViewFieldList
					, group.getConditions(), recordViewRequest));
			whereClause.append(" ) ");
			if(StringUtils.isNotEmpty(group.getOperator())) {
				whereClause.append(group.getOperator());
			}
		}
		
		return whereClause.toString();
	}

	/**
	 * To build dynamic where clause
	 * based on condition
	 * @param recordViewFieldList
	 * @param conditionList
	 * @return
	 */
	private String getColumnNamesInWhereCondtion(List<RecordViewField> recordViewFieldList,
			List<WhereClause> conditionList, RecordViewRequest recordViewRequest) {
		String condition = null;
		if(CollectionUtils.isEmpty(recordViewFieldList) ||  CollectionUtils.isEmpty(conditionList)) {
			return condition;
		}
		StringBuilder sb = new StringBuilder("");
		for(WhereClause where : conditionList) {
			for(RecordViewField recordField : recordViewFieldList) {
				if(recordField.getDisplayName().equalsIgnoreCase(where.getColumnName())) {
					where.setColumnName(recordField.getColumnName());
					break;
				}
			}
			sb.append(where.getColumnName()).append(" ").append(where.getOperator())
			.append("  '").append(where.getConditionValue()).append("'");
			if(StringUtils.isNotEmpty(where.getNextConditionOperator())) {
				sb.append(" ").append(where.getNextConditionOperator()).append(" ");
			}
		}
		condition = sb.toString();
		return condition;
	}

	@Override
	public RecordViewResponse getRoleAndReportConfigList(ReportRequest request) {
		return recordRoleAcessConfigResponseBuilder
				.buildResponse(recordViewService.getRoleAndReportConfigList(request.getIds()));
	}

	@Override
	public RecordViewResponse getRecordViewConfigListBasedOnRecordView(ReportRequest request) {
		return recordViewConfigResponseBuilder
				.buildResponse(recordViewService.getRecordViewConfigListBasedOnRecordView(request.getIds()));
	}

	@Override
	public RecordViewResponse getRecordViewIdAndDisplayNameList(String roleId) {
		return recordViewConfigResponseBuilder
				.buildListOfMapResponse(recordViewService.getRecordViewIdAndDisplayNameList(roleId));
	}

	@Override
	public RecordViewResponse getFieldsConfigListBasedOnRecordView(ReportRequest request) {
		return recordFieldConfigResponseBuilder
				.buildResponse(recordViewService.getFieldsConfigListBasedOnRecordView(request.getIds()));
	}

	@Override
	public RecordView getRecordViewAndItsFields(String recordViewId) {
		RecordView recordView = recordViewBuilder.fromEntity(recordViewService.getRecordViewById(recordViewId));

		List<String> recordViewList = new ArrayList<String>(0);
		recordViewList.add(recordViewId);
		recordView.setRecordViewFieldList(recordViewFieldBuilder
				.fromEntity(recordViewService.getFieldsConfigListBasedOnRecordView(recordViewList)));
		return recordView;
	}

	@Override
	public RecordView getRecordViewAndItsVisibleFields(String recordViewId) {
		RecordView recordView = getRecordViewAndItsFields(recordViewId);
		List<RecordViewField> recordViewList = new ArrayList<RecordViewField>(0);
		StringBuilder sb = new StringBuilder("");
		List<String> maskedColumnList = new ArrayList<String> ();
		
		// Remove invisible field from the list
		for (RecordViewField recordViewField : recordView.getRecordViewFieldList()) {
			if (recordViewField.isVisible() && !recordViewField.isMasked()) {
				recordViewList.add(recordViewField);
				sb.append(recordViewField.getColumnName()).append(" as ")
						.append(recordViewField.getDisplayName().replaceAll("\\s+", ""));
				sb.append(", ");
			}
			if (!sb.toString().isEmpty()) {
				recordView.setCommaSeperatedVisibleColumnList(sb.substring(0, sb.length() - 2));
			}

			if (recordViewField.isMasked()) {
				maskedColumnList.add(recordViewField.getColumnName());
			}

			recordView.setRecordViewFieldList(recordViewList);
		}
		recordView.setMaskedColumnList(maskedColumnList);
		return recordView;
	}

	/**
	 * This method generate native query as string
	 * 
	 * @param tableName
	 * @param ColumnName
	 * @param limit
	 * @param orderBy
	 * @param where
	 * @param groupByColumn
	 * @param countColumn
	 * @param joinTableA
	 * @param tableAColumns
	 * @param tableAIdColumn
	 * @param jointTableB
	 * @param tableBColumns
	 * @param tableBIdColumn
	 * @return
	 */
	public String prepareNativeQuery(RecordViewRequest recordViewRequest, String conditions) {
		StringBuilder sb = new StringBuilder();

		if (StringUtils.isNotEmpty(recordViewRequest.getGroupByColumn())
				&& StringUtils.isNotEmpty(recordViewRequest.getAggFnCol())) {

			sb.append("Select ").append(recordViewRequest.getGroupByColumn()).append(" , ")
					.append(recordViewRequest.getAggFnName()).append("(").append(recordViewRequest.getAggFnCol())
					.append(")").append(" from ").append(recordViewRequest.getTableName()).append(" group by ")
					.append(recordViewRequest.getGroupByColumn());
		} else if (StringUtils.isNotEmpty(recordViewRequest.getAggFnName())
				&& StringUtils.isNotEmpty(recordViewRequest.getAggFnCol())) {

			sb.append("Select ").append(recordViewRequest.getAggFnName()).append("(")
					.append(recordViewRequest.getAggFnCol()).append(")").append(" from ")
					.append(recordViewRequest.getTableName());

		} else if (StringUtils.isNotEmpty(recordViewRequest.getJoinTableA())
				&& StringUtils.isNotEmpty(recordViewRequest.getJoinTableB())
				&& StringUtils.isNotEmpty(recordViewRequest.getTableAIdColumn())
				&& StringUtils.isNotEmpty(recordViewRequest.getTableBIdColumn())) {

			if (StringUtils.isNotEmpty(recordViewRequest.getTableAColumns())
					&& StringUtils.isNotEmpty(recordViewRequest.getTableBColumns())) {

				String tableAColumns = "a." + recordViewRequest.getTableAColumns().replace(",", ",a.");
				String tableBColumns = "b." + recordViewRequest.getTableBColumns().replace(",", ",b.");

				sb.append(" select ").append(tableAColumns).append(", ").append(tableBColumns).append(" from ")
						.append(recordViewRequest.getJoinTableA()).append(" a, ")
						.append(recordViewRequest.getJoinTableB()).append(" b ").append("where a.")
						.append(recordViewRequest.getTableAIdColumn()).append(" = b.")
						.append(recordViewRequest.getTableBIdColumn());
			} else {
				sb.append("Select a.*, b.* from ").append(recordViewRequest.getJoinTableA()).append(" a, ")
						.append(recordViewRequest.getJoinTableB()).append(" b ").append("where a.")
						.append(recordViewRequest.getTableAIdColumn()).append(" = b.")
						.append(recordViewRequest.getTableBIdColumn());
			}
		} else {
			if (StringUtils.isNotEmpty(recordViewRequest.getColumnName())) {
				sb.append("Select ").append(recordViewRequest.getColumnName());

				if (CollectionUtils.isNotEmpty(recordViewRequest.getMaskedColumnList())) {
					String maskedColumns = prepareQueryForMaskedColumns(recordViewRequest.getMaskedColumnList());
					sb.append(", ").append(maskedColumns);
				}
				sb.append(" from ");
			} else {
				sb.append("Select * from ");
			}
			sb.append(recordViewRequest.getTableName());
			if(StringUtils.isNotEmpty(conditions)) {
				sb.append(" where ").append(conditions);
			}
			if (StringUtils.isNotEmpty(recordViewRequest.getWhere())) {
				sb.append(" where ").append(buildWhereClause(recordViewRequest.getWhere()));
			}
		}
		if (StringUtils.isNotEmpty(recordViewRequest.getOrderBy())) {
			sb.append(" ").append("order by").append(" ").append(recordViewRequest.getOrderBy())
			.append(" ").append(recordViewRequest.getOrderByValue());
		}
		if (recordViewRequest.getPageNumber() != null) {
			sb.append(" ").append("limit").append(" ").append(recordViewRequest.getPageNumber()-1)
			.append(", ").append(recordViewRequest.getPerPage());
		}
		
		
		return sb.toString();
	}

	/**
	 * To prepare sql query for masking columns
	 */
	public String prepareQueryForMaskedColumns(List<String> maskedColumnList) {
		String response = null;
		StringBuilder sb = null;
		if (CollectionUtils.isNotEmpty(maskedColumnList)) {
			sb = new StringBuilder(" ");
			for (String maskCol : maskedColumnList) {
				sb.append(createMaskedQuery(maskCol));
				sb.append(",");
			}
			response = sb.substring(0, sb.length() - 1);
		}
		return response;
	}

	/**
	 * TO build where clause
	 * 
	 * @param where
	 * @return
	 */
	public String buildWhereClause(String where) {
		String whereClause = where.replace("34#$E", "=").replace("q19%#", "'").replace("A21t1#2$1", " AND ").replace("021t1#2$2", " OR ")
				.replace("l21t1#2$3", " IN ");
		return whereClause;
	}
	
	/**
	 * TO generate query for masking given column
	 * @param columnName
	 * @return
	 */
	public String createMaskedQuery(String columnName) {
		StringBuilder sb = new StringBuilder("");
		if(columnName != null) {
			sb.append("CONCAT(SUBSTRING(").append(columnName).append(" , 1, 2),REPEAT('x', CHAR_LENGTH(")
			.append(columnName).append(") -4), SUBSTRING(").append(columnName).append(", -2 )) as ").append(columnName);
		}
		
		return sb.toString();
	}

	@Override
	public RecordViewResponse getRecord(RecordViewRequest recordViewRequest) {
		RecordViewResponse recordViewResponse = new RecordViewResponse();

		if (recordViewRequest == null || StringUtils.isEmpty(recordViewRequest.getRecordViewConfigId())) {
			recordViewResponse.setStatus(Status.FAILURE);
			recordViewResponse.setMessage("Invalid request");
			return recordViewResponse;
		}
		if (validateAccess(recordViewRequest.getRecordViewConfigId(), recordViewRequest.getRoleList())) {
			RecordView recordView = getRecordViewAndItsVisibleFields(recordViewRequest.getRecordViewConfigId());
			recordViewRequest.setTableName(recordView.getTableName());
			recordViewRequest.setMaskedColumnList(recordView.getMaskedColumnList());
			recordViewRequest.setColumnName(recordView.getCommaSeperatedVisibleColumnList());
			String conditions = createDynamicWhereClause(recordViewRequest, recordView.getRecordViewFieldList());
			replaceDisplayNameByColumn(recordViewRequest, recordView.getRecordViewFieldList());
			String recordViewQuery = prepareNativeQuery(recordViewRequest, conditions);
			Integer totalCount = getTotalCount(recordViewRequest, conditions);
			List<Map<String, Object>> response = recordViewService.getRecordView(recordViewQuery);
			recordViewResponse = recordViewResponseBuilder.buildResponse(response, totalCount);
		} else {
			recordViewResponse.setStatus(Status.FAILURE);
			recordViewResponse.setMessage("You are not authorized to view this report.");
		}
		return recordViewResponse;
	}

	private void replaceDisplayNameByColumn(RecordViewRequest recordViewRequest,
			List<RecordViewField> recordViewFieldList) {
		if(recordViewRequest != null && CollectionUtils.isNotEmpty(recordViewFieldList)) {
			for(RecordViewField field : recordViewFieldList) {
				if(field.getDisplayName().equalsIgnoreCase(recordViewRequest.getOrderBy())) {
					recordViewRequest.setOrderBy(field.getColumnName());
					break;
				}
			}
		}
	}

	/**
	 * To get total count
	 * @param recordViewRequest
	 * @param conditions
	 * @return
	 */
	private Integer getTotalCount(RecordViewRequest recordViewRequest, String conditions) {
		StringBuilder countQuery = new StringBuilder("Select count(*) from ");
		countQuery.append(recordViewRequest.getTableName());
		if (StringUtils.isNotEmpty(conditions)) {
			countQuery.append(" where ").append(conditions);
		}

		return recordViewService.getRecordViewTotalCount(countQuery.toString());
	}

	/**
	 * This method validate if user has access to particular record view id
	 * 
	 * @param recordConfigId
	 * @param roleList
	 * @return
	 */
	public Boolean validateAccess(String recordConfigId, List<String> roleList) {
		Boolean isValid = false;
		if (StringUtils.isNotEmpty(recordConfigId) && CollectionUtils.isNotEmpty(roleList)) {
			List<RecordRoleConfigEntity> response = recordViewService.getRoleAndReportConfigList(roleList);
			if (CollectionUtils.isNotEmpty(response)) {
				for (RecordRoleConfigEntity recRoleConfig : response) {
					if (recRoleConfig.getRecordViewConfigId().equals(recordConfigId)) {
						isValid = true;
					}
				}
			}
		}
		return isValid;
	}

	@Override
	public SavedFilterResponse getSavedFilterById(String id) {
		RecordViewSavedFilterEntity filter = savedFilterService.getFilterById(id);
		List<RecordViewSavedFilterEntity> filters = new ArrayList<>();
		if (filter != null)
			filters.add(filter);
		return savedFilterResponseBuilder.buildResponse(filters);
	}
	
	@Override
	public SavedFilterResponse createSavedFilter(SavedFilterRequest filterRequest) {
		RecordViewSavedFilterEntity filter = new RecordViewSavedFilterEntity();
		User userDetails = null;
		if(StringUtils.isNotEmpty(filterRequest.getUserId())) {
			userDetails = userService.findByEmail(filterRequest.getUserId());
		}
		filterRequest.setUserId(userDetails.getId());
		BeanUtils.copyProperties(filterRequest, filter);
		filter = savedFilterService.save(filter);
		List<RecordViewSavedFilterEntity> filters = new ArrayList<>();
		filters.add(filter);
		return savedFilterResponseBuilder.buildResponse(filters);
	}
	
	@Override
	public SavedFilterResponse getSavedFiltersByUserId(String userEmailId) {
		User userDetails = null;
		List<RecordViewSavedFilterEntity> filters = null;
		if(StringUtils.isNotEmpty(userEmailId)) {
			userDetails = userService.findByEmail(userEmailId);
		}
		if(userDetails != null) {
			 filters = savedFilterService.getSavedFiltersByUserId(userDetails.getId());
		}
		return savedFilterResponseBuilder.buildResponse(filters);
	}

	@Override
	public SavedFilterResponse updateSavedFilter(SavedFilterRequest filterRequest) {
		RecordViewSavedFilterEntity filter = savedFilterService.getFilterById(filterRequest.getId());
		if (StringUtils.isNotBlank(filterRequest.getFilterName())) {
			filter.setFilterName(filterRequest.getFilterName());
		}
		filter.setIsDeleted(filterRequest.getDelete());
		filter = savedFilterService.save(filter);
		List<RecordViewSavedFilterEntity> filters = new ArrayList<>();
		filters.add(filter);
		return savedFilterResponseBuilder.buildResponse(filters);
	}

	@Override
	public BaseResponse scheduleNotification(RecordViewNotificationDto request) {
		BaseResponse response = new BaseResponse();
		if(request != null) {
			RecordReportsNotficationEntity schedulerEntity =	recordViewSchedulerBuilder.toEntity(request);
			schedulerEntity.setStatus("PENDING");
			schedulerEntity = recordViewService.saveOrUpdateRecordReportNotification(schedulerEntity);
			if(schedulerEntity != null) {
				response.setStatus(Status.SUCCESS);
			}else {
				response.setStatus(Status.FAILURE);
			}
		}
		return response;
	}
}
