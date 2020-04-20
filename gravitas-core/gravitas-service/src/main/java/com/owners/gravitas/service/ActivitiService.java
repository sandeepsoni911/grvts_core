package com.owners.gravitas.service;


public interface ActivitiService {
	
    /**
	 * find latest execution id by process instance id
	 * 
	 * @param procInstanceId
	 * @return
	 */
	String findLatestExecutionId(String procInstanceId);

	/**
	 * find process instance id by execution id
	 * 
	 * @param executionId
	 * @return
	 */
    String findProcessInstanceId(String executionId);
}
