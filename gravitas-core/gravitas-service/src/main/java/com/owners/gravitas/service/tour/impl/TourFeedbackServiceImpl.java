package com.owners.gravitas.service.tour.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.owners.gravitas.service.tour.TourFeedbackService;
import com.owners.gravitas.service.util.RestServiceUtil;
import com.owners.gravitas.util.JsonUtil;
import com.zuner.coshopping.model.api.Response;
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.tour_feedback.PendingEmailFeedback;
import com.zuner.coshopping.model.tour_feedback.TourFeedback;
import com.zuner.coshopping.model.uri.CoShoppingUriConstant;

/**
 * The Class TourFeedbackServiceImpl.
 * 
 * @author rajputbh
 */
@Service("tourFeedbackService")
public class TourFeedbackServiceImpl implements TourFeedbackService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TourFeedbackServiceImpl.class);

    @Value("${gravitas.coshopping_service_api_endpoint}")
    private String coshoppingServiceUrl;

    @Value("${gravitas.tour_feedback.pending_email_list_size:10}")
    private Long tourFeedbackPendingEmailListSize;

    @Autowired
    private RestServiceUtil restServiceUtil;

    /**
     * Creates the agent feedback.
     *
     * @param agentId
     *            the agent id
     * @param tourFeedback
     *            the agent feedback request
     * @return the agent response
     */
    @Override
    public Resource saveTourFeedback(final TourFeedback tourFeedback) {
        String url = coshoppingServiceUrl + CoShoppingUriConstant.BASE_URI + CoShoppingUriConstant.ADD_TOUR_FEEDBACK;
        Response<Resource> tourFeedbackIdResponse = restServiceUtil.postEntityWithGenericResponse(url, tourFeedback,
                new ParameterizedTypeReference<Response<Resource>>() {
                });
        LOGGER.debug("Tour Feedback Hitting POST url : {}, response : {}", url,
                JsonUtil.toJson(tourFeedbackIdResponse));
        return tourFeedbackIdResponse.getResult();
    }

    @Override
    public List<PendingEmailFeedback> getPendingTourFeedbackEmails(final Long createdOn, final Long size) {
        String url = null;
        if (size == null) {
            url = coshoppingServiceUrl + CoShoppingUriConstant.BASE_URI
                    + CoShoppingUriConstant.GET_PENDING_EMAIL_TOUR_FEEDBACK + "?createdOn=" + createdOn;
        } else {
            url = coshoppingServiceUrl + CoShoppingUriConstant.BASE_URI
                    + CoShoppingUriConstant.GET_PENDING_EMAIL_TOUR_FEEDBACK + "?createdOn=" + createdOn + "&size="
                    + tourFeedbackPendingEmailListSize;
        }
        Response<List<PendingEmailFeedback>> pendingemailFeedbackListResponse = restServiceUtil
                .getEntityWithGenericResponse(url,
                        new ParameterizedTypeReference<Response<List<PendingEmailFeedback>>>() {
                        });
        LOGGER.debug("Tour Feedback Hitting GET url : {}", url, JsonUtil.toJson(pendingemailFeedbackListResponse));
        return pendingemailFeedbackListResponse.getResult();
    }

    @Override
    public TourFeedback getTourFeedback(String feedbackId) {
        TourFeedback tourFeedback = null;
        String url = coshoppingServiceUrl + CoShoppingUriConstant.BASE_URI + CoShoppingUriConstant.GET_TOUR_FEEDBACK;
        Map<String, String> params = new HashMap<String, String>();
        params.put("feedbackId", feedbackId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        url = builder.buildAndExpand(params).toUri().toString();
        Response<TourFeedback> tourFeedbackResponse = restServiceUtil.getEntityWithGenericResponse(url,
                new ParameterizedTypeReference<Response<TourFeedback>>() {
                });
        tourFeedback = tourFeedbackResponse.getResult();
        LOGGER.debug("Tour Feedback Hitting GET url : {} tourFeedbackResponse : {}", url,
                JsonUtil.toJson(tourFeedbackResponse));
        return tourFeedback;
    }

    @Override
    public void notifyTourFeedbackEmailSent(final String feedbackId) {
        String url = coshoppingServiceUrl + CoShoppingUriConstant.BASE_URI
                + CoShoppingUriConstant.NOTIFY_TOUR_FEEDBACK_EMAIL_SENT;
        Map<String, String> params = new HashMap<String, String>();
        params.put("feedbackId", feedbackId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        url = builder.buildAndExpand(params).toUri().toString();
        Response<Void> notifyTourFeedbackResponse = restServiceUtil.putEntityWithGenericResponse(url,
                new ParameterizedTypeReference<Response<Void>>() {
                });
        LOGGER.debug("Tour Feedback Hitting PUT url : {}, notifyTourFeedbackEmailSent : {}", url,
                JsonUtil.toJson(notifyTourFeedbackResponse));
    }

    @Override
    public void notifyTourFeedbackEmailStatus(final String feedbackId, final String emailStatus) {
        String url = coshoppingServiceUrl + CoShoppingUriConstant.BASE_URI
                + CoShoppingUriConstant.NOTIFY_TOUR_FEEDBACK_EMAIL_STATUS;
        Map<String, String> params = new HashMap<String, String>();
        params.put("feedbackId", feedbackId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        url = builder.buildAndExpand(params).toUri().toString();
        url = url + "?emailStatus=" + emailStatus;
        Response<Void> notifyTourFeedbackResponse = restServiceUtil.putEntityWithGenericResponse(url,
                new ParameterizedTypeReference<Response<Void>>() {
                });
        LOGGER.debug("Tour Feedback Hitting PUT url : {}, notifyTourFeedbackEmailStatus : {}", url,
                JsonUtil.toJson(notifyTourFeedbackResponse));
    }

    @Override
    public void updateTourFeedbackImageUploadStatus(final String feedbackId, final String noteId, final String fileId,
            final String uploadStatus) {
        String url = coshoppingServiceUrl + CoShoppingUriConstant.BASE_URI
                + CoShoppingUriConstant.UPDATE_TOUR_FEEDBACK_IMAGE_UPLOAD_STATUS;
        Map<String, String> params = new HashMap<String, String>();
        params.put("feedbackId", feedbackId);
        params.put("noteId", noteId);
        params.put("fileId", fileId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("uploadStatus", uploadStatus);
        url = builder.buildAndExpand(params).toUri().toString();
        Response<Void> notifyTourFeedbackResponse = restServiceUtil.putEntityWithGenericResponse(url,
                new ParameterizedTypeReference<Response<Void>>() {
                });
        LOGGER.debug("Tour Feedback Hitting PUT url : {}, updateTourFeedbackImageUploadStatus : {}", url,
                JsonUtil.toJson(notifyTourFeedbackResponse));
    }
}
