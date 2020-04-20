package com.owners.gravitas.dao;

public interface ActExecutionDao {

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
