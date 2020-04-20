package com.owners.gravitas.service.tour.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hubzu.common.daemon.service.DaemonScheduler;
import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.config.tour.TourFeedbackJmxConfig;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.tour.TourFeedbackEmailScheduler;
import com.owners.gravitas.service.tour.TourFeedbackService;
import com.zuner.coshopping.model.tour_feedback.PendingEmailFeedback;

@Component
@EnableScheduling
public class FeedbackEmailSchedulerImpl extends DaemonScheduler implements TourFeedbackEmailScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackEmailSchedulerImpl.class);

    private final String TOUR_FEEDBACK_EMAIL_SCHEDULER_FETCH_LOCK = "TOUR_FEEDBACK_EMAIL_SCHEDULER_FETCH_LOCK";

    final String SUCCESS = "SUCCESS";
    final String ERROR = "ERROR";
    @Autowired
    private DistributedSynchronizationService databaseDistributedSynchronizationService;

    @Autowired
    private TourFeedbackService tourFeedbackService;

    @Autowired
    private TourFeedbackJmxConfig tourFeedbackJmxConfig;

    @Autowired
    private TourFeedbackEmailProcessorImpl tourFeedbackEmailProcessorImpl;

    @Autowired
    @Qualifier("tourFeedbackEmailThreadPool")
    private ExecutorService tourFeedbackEmailThreadPool;

    @Scheduled(fixedDelayString = "${gravitas.tour_feedback.email_scheduler_delay:5000}")
    @Override
    public void fetchPendingFeedbackEmails() {
        if (stopScheduler) {
            LOGGER.info("Tour Feedback Stoping FeedbackEmailSchedulerImpl Job  : {}",
                    new java.sql.Timestamp(System.currentTimeMillis()));
            return;
        }

        long t1 = System.currentTimeMillis();
        if (tourFeedbackJmxConfig.isSendEmailFlag()) {
            if (databaseDistributedSynchronizationService.acquireLock(TOUR_FEEDBACK_EMAIL_SCHEDULER_FETCH_LOCK)) {
                try {
                    List<PendingEmailFeedback> pendingEmailFeedbackList = tourFeedbackService
                            .getPendingTourFeedbackEmails(System.currentTimeMillis()
                                    - tourFeedbackJmxConfig.getSendEmailWaitTimeMin() * 60 * 1000, 100l);
                    if (!CollectionUtils.isEmpty(pendingEmailFeedbackList)) {
                        Map<String, Future<String>> results = new HashMap<>();
                        LOGGER.info("Tour Feedback pendingemailFeedbackList : {}", pendingEmailFeedbackList);
                        for (PendingEmailFeedback pendingEmailFeedback : pendingEmailFeedbackList) {
                            if (pendingEmailFeedback != null) {
                                String feedbackId = pendingEmailFeedback.getFeedbackId();
                                try {
                                    results.put(feedbackId, tourFeedbackEmailThreadPool
                                            .submit(new FeedbackEmailProcessorTask(feedbackId)));
                                } catch (Exception e) {
                                    LOGGER.error("Tour Feedback Thread Pool Submit Failed : {}", feedbackId, e);
                                }
                            }
                        }

                        for (String feedbackId : results.keySet()) {
                            try {
                                String emailResult = results.get(feedbackId).get();
                                LOGGER.info("INFO Tour Feedback Thread Pool feedbackId : {}, emailResult : {}", feedbackId,
                                        emailResult);
                            } catch (InterruptedException | ExecutionException | ApplicationException e) {
                                LOGGER.error("Thread Pool Fetch Result Failed - feedbackId : {}", feedbackId, e);
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Tour Feedback Error in email processing", e);
                } finally {
                    databaseDistributedSynchronizationService.releaseLock(TOUR_FEEDBACK_EMAIL_SCHEDULER_FETCH_LOCK);
                }
            } else {
                LOGGER.info("Tour Feedback Distributed Lock NOT acquired for :{}", TOUR_FEEDBACK_EMAIL_SCHEDULER_FETCH_LOCK);
            }
            LOGGER.info("INFO Tour Feedback Completed fetchPendingFeedbackEmails - Time Consumed : {} ",
                    (System.currentTimeMillis() - t1));
        }
    }

    private class FeedbackEmailProcessorTask implements Callable<String> {
        private String feedbackId;

        public FeedbackEmailProcessorTask(final String feedbackId) {
            this.feedbackId = feedbackId;
        }

        @Override
        public String call() {
            String emailResult = null;
            try {
                LOGGER.info("INFO Executing Tour Feedback Email Processor Thread => feedbackId : {}", feedbackId);
                tourFeedbackEmailProcessorImpl.processFeedbackEmails(feedbackId);
                emailResult = SUCCESS;
            } catch (Exception e) {
                LOGGER.error("Tour Feedback feedbackId : {}", feedbackId, e);
                emailResult = ERROR;
            }
            return emailResult;
        }
    }
}
