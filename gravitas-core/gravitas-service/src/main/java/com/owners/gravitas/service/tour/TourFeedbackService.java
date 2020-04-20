package com.owners.gravitas.service.tour;

import java.util.List;

import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.tour_feedback.PendingEmailFeedback;
import com.zuner.coshopping.model.tour_feedback.TourFeedback;

/**
 * The Interface TourFeedbackService.
 * 
 * @author rajputbh
 */
public interface TourFeedbackService {

    public Resource saveTourFeedback(final TourFeedback tourFeedback);

    public List<PendingEmailFeedback> getPendingTourFeedbackEmails(final Long createdOn, final Long size);

    public TourFeedback getTourFeedback(String feedbackId);

    public void notifyTourFeedbackEmailSent(final String feedbackId);

    public void updateTourFeedbackImageUploadStatus(final String feedbackId, final String noteId, final String fileId,
            final String uploadStatus);
    
    public void notifyTourFeedbackEmailStatus(final String feedbackId, final String emailStatus);
}
