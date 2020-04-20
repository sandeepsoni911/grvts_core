package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.UserPermission.ADD_TOUR_FEEDBACK;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.annotation.ReadArgs;
import com.owners.gravitas.business.tour.TourFeedbackBusiness;
import com.owners.gravitas.dto.tour.TourFeedbackResponse;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.validator.UserRoleValidator;
import com.zuner.coshopping.model.tour_feedback.TourFeedback;

/**
 * Test TourFeedbackController
 *
 * @author rajputbh
 */
@RestController
public class TourFeedbackController extends BaseController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TourFeedbackController.class);

    @Autowired
    private TourFeedbackBusiness tourFeedbackBusiness;

    /** The role validator. */
    @Autowired
    private UserRoleValidator roleValidator;

    /**
     * Creates the tour feedback.
     *
     * @param agentId
     *            the agent id
     * @param agentFeedbackRequest
     *            the agent feedback request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping(value = "/agents/{agentId}/feedbacks", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ReadArgs
    @PerformanceLog
    @Secured({ ADD_TOUR_FEEDBACK })
    public TourFeedbackResponse createAgentTourFeedback(@PathVariable final String agentId,
            @Valid @RequestBody final TourFeedback tourFeedback) {
        roleValidator.validateByAgentId(agentId);
        LOGGER.info("Tour Feedback  - createAgentTourFeedback agentId : {}, tourFeedback request : {}", agentId,
                JsonUtil.toJson(tourFeedback));
        return tourFeedbackBusiness.createTourFeedback(agentId, tourFeedback);
    }

}
