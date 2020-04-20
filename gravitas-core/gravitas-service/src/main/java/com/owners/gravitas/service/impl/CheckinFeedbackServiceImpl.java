package com.owners.gravitas.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.CheckinFeedback;
import com.owners.gravitas.repository.CheckinFeedbackRepository;
import com.owners.gravitas.service.CheckinFeedbackService;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckinFeedbackServiceImpl.
 *
 * @author amits
 */
@Service( value = "checkinFeedbackService" )
public class CheckinFeedbackServiceImpl implements CheckinFeedbackService {

    /** The checkin feedback repository. */
    @Autowired
    private CheckinFeedbackRepository checkinFeedbackRepository;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.CheckinFeedbackService#save(com.owners.
     * gravitas.domain.entity.CheckinFeedback)
     */
    @Override
    public void saveAll( Collection< CheckinFeedback > feedback ) {
        checkinFeedbackRepository.save( feedback );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.CheckinFeedbackService#getCheckinFeedback(com
     * .owners.gravitas.domain.entity.AgentTask, java.util.Collection)
     */
    @Override
    public Collection< CheckinFeedback > getCheckinFeedback( AgentTask task ) {
        return checkinFeedbackRepository.findByTask( task );
    }

    /**
     * Delete checkins.
     *
     * @param checkins
     *            the checkins
     */
    @Override
    public void deleteCheckins( Collection< CheckinFeedback > checkins ) {
        checkinFeedbackRepository.delete( checkins );
    }

}
