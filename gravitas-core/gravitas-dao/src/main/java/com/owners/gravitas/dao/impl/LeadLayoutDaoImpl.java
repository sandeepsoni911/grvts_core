package com.owners.gravitas.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.dao.LeadLayoutDao;
import com.owners.gravitas.domain.entity.LeadLayout;
import com.owners.gravitas.repository.LeadLayoutRepository;

@Repository
public class LeadLayoutDaoImpl implements LeadLayoutDao {

	/** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadLayoutDaoImpl.class );
	
	@Autowired
	private LeadLayoutRepository leadLayoutRepository;
		
	@Override
	public String getLeadLayout(String emailId, String type, String source) {
		LOGGER.info( "Start of LeadLayoutDaoImpl getLeadLayout method email : {}, type : {}, source : {}", emailId, type, source );
		String layout = null;
		List<LeadLayout> leadLayouts = leadLayoutRepository.findByEmailIdAndTypeAndSource(emailId, type, source);
		if(leadLayouts != null && leadLayouts.size() > 0){
			LeadLayout leadLayout = leadLayouts.get(0);
			if(leadLayout != null && leadLayout.getLayout() != null){
				layout = leadLayout.getLayout();
			}
		}
		LOGGER.info( "End of LeadLayoutDaoImpl getLeadLayout method layout : {}", layout );
		return layout;
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRES_NEW )
	public void saveOrUpdateLeadLayout(String emailId, String type, String source, String layout) {
		LOGGER.info( "Start of LeadLayoutDaoImpl saveOrUpdateLeadLayout method email : {}, type : {}, source : {}", emailId, type, source );
		List<LeadLayout> leadLayouts = leadLayoutRepository.findByEmailIdAndTypeAndSource(emailId, type, source);
		LeadLayout leadLayout = null;
		if(leadLayouts != null && leadLayouts.size() > 0){
			leadLayout = leadLayouts.get(0);
			leadLayout.setLayout(layout);
		}else{
			leadLayout = new LeadLayout();
			leadLayout.setEmailId(emailId);
			leadLayout.setType(type);
			leadLayout.setSource(source);
			leadLayout.setLayout(layout);
		}
		leadLayout = leadLayoutRepository.save(leadLayout);
		LOGGER.info( "End of LeadLayoutDaoImpl saveOrUpdateLeadLayout method layoutid : {}", leadLayout.getId() );
	}
	
}
