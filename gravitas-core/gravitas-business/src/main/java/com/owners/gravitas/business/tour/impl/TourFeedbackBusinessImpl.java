package com.owners.gravitas.business.tour.impl;

import static com.owners.gravitas.enums.ErrorCode.FILE_SIGNED_S3_URL_GENERATION_ERROR;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.owners.gravitas.business.builder.tour.TourFeedbackObjectBuilder;
import com.owners.gravitas.business.tour.TourFeedbackBusiness;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.tour.TourFeedbackResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.AmazonS3Service;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.tour.TourFeedbackService;
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.tour_feedback.File;
import com.zuner.coshopping.model.tour_feedback.Note;
import com.zuner.coshopping.model.tour_feedback.TourFeedback;

/**
 * The Class TourFeedbackBusinessImpl
 *
 * @author rajputbh
 */
@Service("tourFeedbackBusiness")
public class TourFeedbackBusinessImpl implements TourFeedbackBusiness {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TourFeedbackBusinessImpl.class);

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Value("${gravitas.amazon.s3.tour_feedback.image_bucket_name:gravitas-feeback}")
    private String gravitasTourFeedbackS3BucketName;

    @Autowired
    private TourFeedbackService tourFeedbackService;
    
    @Autowired
    private AgentTaskService agentTaskService;
    
    @Autowired
    private ContactEntityService contactServiceV1;

    /**
     * Creates the agent feedback.
     *
     * @param agentId
     *            the agent id
     * @param agentFeedbackRequest
     *            the agent feedback request
     * @return the agent response
     */
    @Override
    public TourFeedbackResponse createTourFeedback(final String agentId, final TourFeedback tourFeedbackRequest) {
        TourFeedback tourFeedback = getTourFeedback(agentId, tourFeedbackRequest);
        Task agentTask = agentTaskService.getTaskById( tourFeedback.getAgentId(), tourFeedback.getTaskId() );
        com.owners.gravitas.domain.entity.Contact contact = contactServiceV1
                .getContactByFbOpportunityId( agentTask.getOpportunityId() );
        tourFeedback.setUserId(contact.getOwnersComId());
        tourFeedback.setBuyerEmailId(contact.getEmail());
        tourFeedback.setTourTime(agentTask.getDueDtm());
        Map<String, String> urlMap = getSignedS3UrlMap(tourFeedback.getId(), tourFeedback);
        Map<String, String> signedS3Urls = setAndGetSignedS3UrlMap(tourFeedback, urlMap);
        if (urlMap != null) {
            tourFeedback.setTotalFileCount(urlMap.size());
            tourFeedback.setUploadedFileCount(0);
        }
        Resource resource = tourFeedbackService.saveTourFeedback(tourFeedback);
        TourFeedbackResponse tourFeedbackResponse = new TourFeedbackResponse();
        tourFeedbackResponse.setId(resource.getId());
        tourFeedbackResponse.setSignedS3Urls(signedS3Urls);
        return tourFeedbackResponse;
    }

    private TourFeedback getTourFeedback(String agentId, TourFeedback tourFeedbackRequest) {
        TourFeedback tourFeedback = new TourFeedbackObjectBuilder().from(tourFeedbackRequest, agentId)
                .setUploadedFileCount(0).build();
        return tourFeedback;
    }

    private Map<String, String> setAndGetSignedS3UrlMap(TourFeedback tourFeedback, Map<String, String> signedS3UrlMap) {
        Map<String, String> signedS3Urls = null;
        if (signedS3UrlMap != null) {
            signedS3Urls = new HashMap<String, String>();
            if (tourFeedback != null) {
                if (!CollectionUtils.isEmpty(tourFeedback.getNotes())) {
                    for (com.zuner.coshopping.model.tour_feedback.Note noteRequest : tourFeedback.getNotes()) {
                        if (noteRequest != null && !CollectionUtils.isEmpty(noteRequest.getFiles())) {
                            for (File file : noteRequest.getFiles()) {
                                if (file != null) {
                                    signedS3Urls.put(file.getNameCode(), signedS3UrlMap.get(file.getNameCode()));
                                    file.setFileUrl(signedS3UrlMap.get(file.getNameCode()));
                                }
                            }
                        }
                    }
                }
            }
        }
        return signedS3Urls;
    }

    private Map<String, String> getSignedS3UrlMap(String feedbackId, TourFeedback tourFeedback) {
        if (tourFeedback != null) {
            Map<String, String> urlMap = new HashMap<String, String>();
            if (!CollectionUtils.isEmpty(tourFeedback.getNotes())) {
                for (Note note : tourFeedback.getNotes()) {
                    if (note != null && !CollectionUtils.isEmpty(note.getFiles())) {
                        for (File file : note.getFiles()) {
                            if (file != null && !StringUtils.isEmpty(tourFeedback.getId())
                                    && !StringUtils.isEmpty(note.getId()) && !StringUtils.isEmpty(file.getId())) {
                                if (!urlMap.containsKey(file.getNameCode())) {
                                    String objectKey = "tour_feedbacks/" + tourFeedback.getId() + "/" + note.getId()
                                            + "/" + file.getId();
                                    URL url = null;
                                    try {
                                        url = amazonS3Service.getSignedUrl(objectKey, gravitasTourFeedbackS3BucketName,
                                                file.getType());
                                        urlMap.put(file.getNameCode(), url.toString());
                                    } catch (AmazonClientException | IllegalArgumentException | IOException e) {
                                        LOGGER.error(
                                                "Tour Feedback Signed S3 URL generation error, tourFeedbackRequest : {}",
                                                tourFeedback.toString(), e);
                                        throw new ApplicationException("Tour Feedback Signed S3 URL generation error",
                                                e, FILE_SIGNED_S3_URL_GENERATION_ERROR);
                                    } catch (Exception e) {
                                        LOGGER.error(
                                                "Tour Feedback Signed S3 URL generation error, tourFeedbackRequest : {}",
                                                tourFeedback.toString(), e);
                                        throw new ApplicationException("Tour Feedback Signed S3 URL generation error",
                                                e, FILE_SIGNED_S3_URL_GENERATION_ERROR);
                                    }
                                } else {
                                    throw new ApplicationException(
                                            "Tour Feedback Signed S3 URL generation error for feedbackId : "
                                                    + tourFeedback.getId() + ", Error : Duplicate File Name Code",
                                            FILE_SIGNED_S3_URL_GENERATION_ERROR);
                                }
                            } else {
                                throw new ApplicationException(
                                        "Tour Feedback Signed S3 URL generation error for feedbackId : "
                                                + tourFeedback.getId() + ", noteId : " + note.getId() + ", fileId :"
                                                + file.getId(),
                                        FILE_SIGNED_S3_URL_GENERATION_ERROR);
                            }
                        }
                    }
                }
            }
            return urlMap;
        }
        return null;
    }
}
