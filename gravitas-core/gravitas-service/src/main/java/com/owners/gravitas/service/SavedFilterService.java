package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.RecordViewSavedFilterEntity;

/**
 * To contain all contracts related
 * to filters features
 * @author sandeepsoni
 *
 */
public interface SavedFilterService {

	/**
	 * To save filters
	 * @param filter
	 * @return {@link RecordViewSavedFilterEntity}
	 */
	public RecordViewSavedFilterEntity save(RecordViewSavedFilterEntity filter);
	
	/**
	 * To get filters by 
	 * filter id
	 * @param id
	 * @return {@link RecordViewSavedFilterEntity}
	 */
	public RecordViewSavedFilterEntity getFilterById(String id);

	/**
	 * To get all saved
	 * filters by user id
	 * @param userId
	 * @return {@link List<RecordViewSavedFilterEntity>}
	 */
	public List<RecordViewSavedFilterEntity> getSavedFiltersByUserId(String userId);

}
