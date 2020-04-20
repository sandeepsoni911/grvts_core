package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.BLANK;
import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.enums.LeadStatus.LOST;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.dao.ContactDao;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.LeadSyncUp;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.repository.ContactRepository;
import com.owners.gravitas.repository.LeadSyncUpRepository;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.LeadSyncUpEntityService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;

/**
 * The Class LeadSyncUpEntityServiceImpl.
 * 
 * @author kushwaja
 *         The Class LeadSyncUpEntityServiceImpl.
 */
@Service
public class LeadSyncUpEntityServiceImpl implements LeadSyncUpEntityService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ContactEntityServiceImplV1.class );

    /** The contact repository. */
    @Autowired
    private LeadSyncUpRepository leadSyncUpRepository;
    


	@Override
	@Transactional( propagation = Propagation.REQUIRES_NEW )
	public LeadSyncUp save(LeadSyncUp leadSyncUp) {
		return leadSyncUpRepository.save( leadSyncUp );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRES_NEW )
	public List<LeadSyncUp> save(List<LeadSyncUp> LeadSyncUpEntities) {
		 return leadSyncUpRepository.save( LeadSyncUpEntities );
	}
}
