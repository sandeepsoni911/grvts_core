package com.owners.gravitas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.ContactLog;
import com.owners.gravitas.repository.ContactLogRepository;
import com.owners.gravitas.service.ContactLogService;

@Service
public class ContactLogServiceImpl implements ContactLogService {
    @Autowired
    private ContactLogRepository contactLogRepository;

    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public ContactLog save( ContactLog contactLog ) {
        return contactLogRepository.save( contactLog );
    }
    
    @Override
    public List<ContactLog> findByContactIdOrderByCreatedDateDesc( String contactId ) {
        return contactLogRepository.findByContactIdOrderByCreatedDateDesc(contactId);
    }
    
    @Override
    @Async( value = "apiExecutor" )
    @Transactional( propagation = Propagation.REQUIRED )
    public ContactLog saveAsync( ContactLog contactLog ) {
        return contactLogRepository.save( contactLog );
    }
}
