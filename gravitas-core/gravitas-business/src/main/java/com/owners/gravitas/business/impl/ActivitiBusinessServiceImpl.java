package com.owners.gravitas.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.ActivitiBusinessService;
import com.owners.gravitas.service.ActivitiService;

@Service
@Transactional
public class ActivitiBusinessServiceImpl implements ActivitiBusinessService {

	@Autowired
	private ActivitiService activitiService;
	
	@Override
	public String findLatestExecutionId(String procInstanceId) {
		return activitiService.findLatestExecutionId(procInstanceId);
	}

}
