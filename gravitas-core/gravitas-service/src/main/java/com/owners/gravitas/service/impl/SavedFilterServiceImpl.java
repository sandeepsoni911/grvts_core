package com.owners.gravitas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.RecordViewSavedFilterEntity;
import com.owners.gravitas.repository.SavedFilterRepository;
import com.owners.gravitas.service.SavedFilterService;
/**
 * Implementation class for Saved filter
 * @author sonisan
 *
 */
@Service
public class SavedFilterServiceImpl implements SavedFilterService {
	
	@Autowired
	SavedFilterRepository savedFilterRepository;

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public RecordViewSavedFilterEntity save( RecordViewSavedFilterEntity filter ) {
		return savedFilterRepository.save( filter );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public RecordViewSavedFilterEntity getFilterById(String id) {
		return savedFilterRepository.findOne(id);
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public List<RecordViewSavedFilterEntity> getSavedFiltersByUserId(String userId) {
		return savedFilterRepository.findFiltersByUserId(userId);
	}
}
