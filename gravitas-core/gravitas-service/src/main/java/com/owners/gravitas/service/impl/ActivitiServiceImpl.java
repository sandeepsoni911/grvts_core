package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.dao.ActExecutionDao;
import com.owners.gravitas.service.ActivitiService;

@Service
public class ActivitiServiceImpl implements ActivitiService {

	@Autowired
	private ActExecutionDao executionDao;
		
	@Override
	public String findLatestExecutionId(String procInstanceId) {
		return executionDao.findLatestExecutionId(procInstanceId);
	}
	
	@Override
	public String findProcessInstanceId(String executionId) {
	    return executionDao.findProcessInstanceId( executionId );
	}
}
