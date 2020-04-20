package com.owners.gravitas.service.tour.impl;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.constants.NotificationParameters.HIDE_ADDRESS;
import static com.owners.gravitas.constants.NotificationParameters.TASK_ID;
import static com.owners.gravitas.constants.NotificationParameters.TOUR_FEEDBACK_ID;
import static com.owners.gravitas.constants.NotificationParameters.USER_ID;
import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.hubzu.notification.dto.client.digest.DigestData;
import com.hubzu.notification.dto.client.digest.DigestEmailNotification;
import com.hubzu.notification.dto.client.digest.DigestSection;
import com.hubzu.notification.dto.client.digest.DigestSectionItem;
import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.config.tour.TourFeedbackJmxConfig;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.service.ActionLogService;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.PropertyService;
import com.owners.gravitas.service.tour.TourFeedbackEmailProcessor;
import com.owners.gravitas.service.tour.TourFeedbackService;
import com.owners.gravitas.util.JsonUtil;
import com.zuner.coshopping.model.tour_feedback.Note;
import com.zuner.coshopping.model.tour_feedback.TourFeedback;
import com.zuner.coshopping.model.tour_feedback.TourFeedbackEmailStatus;

@Service
public class TourFeedbackEmailProcessorImpl implements TourFeedbackEmailProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TourFeedbackEmailProcessorImpl.class);

    private final String TOUR_FEEDBACK_EMAIL_PROCESS_LOCK = "TOUR_FEEDBACK_EMAIL_PROCESS_LOCK";
    private final String TOUR_FEEDBACK_DIGEST_NOTIFICATION = "TOUR_FEEDBACK_DIGEST_NOTIFICATION";
    private final String TOUR_TIME_FORMAT_STRING = "MM/dd/yy' at 'hh:mm a";

    @Autowired
    private DistributedSynchronizationService databaseDistributedSynchronizationService;

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private TourFeedbackService tourFeedbackService;
    @Autowired
    private AgentDetailsService agentDetailsService;
    @Autowired
    private TourFeedbackJmxConfig tourFeedbackJmxConfig;
    @Autowired
    private ActionLogService actionLogService;
    @Autowired
    private MailService mailService;
    @Autowired
    private AgentTaskService agentTaskService;
    @Autowired
    private AgentService agentService;

    private String getAgentEmail(String agentEmail) {
        LOGGER.info("Tour Feedback Agent's email is : {} for agentId : {}", agentEmail);
        // this code was added since we do not have the from domain
        // registered and hence for testing this can be used where we set the
        // from email as per our valid domain
        if (tourFeedbackJmxConfig.isUseStaticFromEmail()
                && !StringUtils.isEmpty(tourFeedbackJmxConfig.getStaticFromEmail())) {
            agentEmail = tourFeedbackJmxConfig.getStaticFromEmail();
        }
        return agentEmail;
    }

    private String getEmailSubject(final String location) {
        String actualSubject = "";
        String jmxSubject = tourFeedbackJmxConfig.getSendEmailSubject();
        if (StringUtils.isNotBlank(jmxSubject)) {
            if (location != null) {
                actualSubject = jmxSubject.replace("$LOCATION$", location);
            } else {
                actualSubject = jmxSubject.replace("$LOCATION$", "");
            }
        }
        return actualSubject;
    }

    private void notifyEmailStatusToCoshopping(final String feedbackId, final String status) {
        try {
            tourFeedbackService.notifyTourFeedbackEmailStatus(feedbackId, status);
        } catch (Exception e) {
            LOGGER.error(
                    "Tour Feedback Send Email Error notifying coshopping about email status - feedbackId : {}, status : {}",
                    feedbackId, status, e);
        }
    }

    private void saveTourReportActionLog(final String actionLogSentStatus, final TourFeedback tourFeedback,
            Task agentTask, final String agentEmail) {
        ActionLog actionLog = new ActionLog();
        try {
            actionLog.setActionBy(agentEmail);
            actionLog.setFieldName("tourReportEmailStatus");
            actionLog.setActionEntity(OPPORTUNITY.name());

            String actionEntityId = null;
            if (agentTask != null) {
                try {
                    LOGGER.info(
                            "Tour Feedback Send Email Fetch Opportunity - tourFeedback : {}, agentEmail : {}, actionLogSentStatus : {}, agentTask : {}",
                            JsonUtil.toJson(tourFeedback), agentEmail, actionLogSentStatus, agentTask.toString());
                    actionEntityId = agentTask.getOpportunityId();
                } catch (Exception e) {
                    LOGGER.error(
                            "Tour Feedback Send Email Fetch Opportunity Error - tourFeedback : {}, agentEmail : {}, actionLogSentStatus : {}, agentTask : {}",
                            JsonUtil.toJson(tourFeedback), agentEmail, actionLogSentStatus, agentTask.toString(), e);
                }
            } else {
                LOGGER.info(
                        "Tour Feedback Send Email Fetch Opportunity agentTask is NULL - tourFeedback : {}, agentEmail : {}, actionLogSentStatus : {}",
                        JsonUtil.toJson(tourFeedback), agentEmail, actionLogSentStatus);
            }
            if (!StringUtils.isEmpty(actionEntityId)) {
                actionLog.setActionEntityId(actionEntityId);
            } else {
                LOGGER.error(
                        "Tour Feedback Send Email Fetch Opportunity - action entity id(i.e. opportunity id) Error - tourFeedback : {}, agentEmail : {}, actionLogSentStatus : {}",
                        JsonUtil.toJson(tourFeedback), agentEmail, actionLogSentStatus);
            }

            if ("SUCCESS".equals(actionLogSentStatus)) {
                actionLog.setActionType("TOUR_REPORT_EMAIL_SENT");
            } else {
                actionLog.setActionType("TOUR_REPORT_EMAIL_FAILED");
            }
            actionLog.setCreatedBy("GRAVITAS");
            DateTime createdDate = new DateTime(System.currentTimeMillis());
            actionLog.setCreatedDate(createdDate);
            actionLog.setPlatform("AGENT_APP");
            actionLog = actionLogService.save(actionLog);
            LOGGER.info(
                    "Tour Feedback saveTourReportActionLog success - actionLog : {}, tourFeedback : {}, agentEmail : {}, actionLogSentStatus : {}",
                    JsonUtil.toJson(actionLog), JsonUtil.toJson(tourFeedback), agentEmail, actionLogSentStatus);
        } catch (Exception e) {
            LOGGER.error(
                    "Tour Feedback Send Email saveTourReportActionLog Error - tourFeedback : {}, agentEmail : {}, actionLogSentStatus : {}, actionLog : {}",
                    JsonUtil.toJson(tourFeedback), agentEmail, actionLogSentStatus, JsonUtil.toJson(actionLog), e);
        }
    }

    private void sendActualEmail(final String feedbackId, final TourFeedback tourFeedback) {
        Task agentTask = agentTaskService.getTaskById( tourFeedback.getAgentId(), tourFeedback.getTaskId() );
        Agent agent = agentService.getAgentById( tourFeedback.getAgentId() );
        AgentInfo agentInfo = null != agent ? agent.getInfo() : new AgentInfo();
        String agentEmail = getAgentEmail(agentInfo.getEmail());
        String buyerEmail = tourFeedback.getBuyerEmailId();
        String actionLogSentStatus = "FAILURE";
        String sendStatus = TourFeedbackEmailStatus.FAILED_OTHER.name();
        LOGGER.info("Tour Feedback Send Email - tourFeedback : {}, agentEmail : {}, buyerEmail : {}",
                JsonUtil.toJson(tourFeedback), agentEmail, buyerEmail);
        try {
            if (!StringUtils.isEmpty(agentEmail) && !StringUtils.isEmpty(buyerEmail)) {
                DigestEmailNotification digestEmailNotification = getDigestEmailNotification(agentEmail, buyerEmail,
                        tourFeedback, agentTask);
                digestEmailNotification.setClientId(NOTIFICATION_CLIENT_ID);
                LOGGER.info("Tour Feedback Sending Email Request tourFeedback : {}, digestEmailNotification : {}, ",
                        JsonUtil.toJson(tourFeedback), JsonUtil.toJson(digestEmailNotification));
                NotificationResponse notificationResponse = mailService.sendDigest(digestEmailNotification);
                if (notificationResponse != null) {
                    LOGGER.info(
                            "Tour Feedback Email Response tourFeedback : {}, digestEmailNotification : {}, notificationResponse : {}, ",
                            JsonUtil.toJson(tourFeedback), JsonUtil.toJson(digestEmailNotification),
                            JsonUtil.toJson(notificationResponse));
                    if (notificationResponse.getStatus().equals("SUCCESS")) {
                        sendStatus = TourFeedbackEmailStatus.SENT.name();
                        actionLogSentStatus = "SUCCESS";
                    } else {
                        sendStatus = TourFeedbackEmailStatus.FAILED_OTHER.name();
                    }
                } else {
                    sendStatus = TourFeedbackEmailStatus.FAILED_OTHER.name();
                }
            } else {
                if (StringUtils.isEmpty(agentEmail)) {
                    sendStatus = TourFeedbackEmailStatus.FAILED_AGENT_EMAIL_NOT_FOUND.name();
                } else if (StringUtils.isEmpty(buyerEmail)) {
                    sendStatus = TourFeedbackEmailStatus.FAILED_BUYER_EMAIL_NOT_FOUND.name();
                }
            }
            saveTourReportActionLog(actionLogSentStatus, tourFeedback, agentTask, agentEmail);
            notifyEmailStatusToCoshopping(feedbackId, sendStatus);
        } catch (Exception e) {
            LOGGER.error(
                    "Tour Feedback Send Email FAILED - tourFeedback : {}, agentEmail : {}, buyerEmail : {},  actionLogSentStatus : {}, sendStatus : {}",
                    tourFeedback, agentEmail, buyerEmail, actionLogSentStatus, sendStatus, e);
        }
        LOGGER.error(
                "INFO - Tour Feedback Email Sent Final Status - tourFeedback : {}, actionLogSentStatus : {}, sendStatus : {}",
                tourFeedback, actionLogSentStatus, sendStatus);
    }

    private DigestEmailNotification getDigestEmailNotification(String fromEmail, final String to,
            TourFeedback tourFeedback, Task agentTask ) {
        DigestEmailNotification digestEmailNotification = new DigestEmailNotification();
        digestEmailNotification.setClientId(NOTIFICATION_CLIENT_ID);
        DigestData digestData = new DigestData();
        String applicationDigestId = UUID.randomUUID().toString();
        digestData.setApplicationDigestId(applicationDigestId);
        List<DigestSection> sections = new ArrayList<DigestSection>();
        DigestSection digestSection = new DigestSection();
        digestSection.setKey("NOTES");
        if (!org.apache.commons.collections.CollectionUtils.isEmpty(tourFeedback.getNotes())) {
            List<DigestSectionItem> items = new ArrayList<DigestSectionItem>();
            int sequence = 0;
            for (Note note : tourFeedback.getNotes()) {
                DigestSectionItem digestSectionItem = new DigestSectionItem();
                digestSectionItem.setId(note.getId());
                Map<String, String> parameters = new HashMap<String, String>();
                // TODO: presently consider only image file for each note
                if (note.getFiles() != null && note.getFiles().size() > 0) {
                    String url = note.getFiles().get(0).getFileUrl();
                    if (url != null && url.indexOf("?") > -1) {
                        url = url.substring(0, url.indexOf("?"));
                    }
                    parameters.put("IMAGE_URL", url);
                }
                parameters.put("COMMENT", note.getComment());
                parameters.put("CREATED_ON", note.getCreatedOn() + "");
                parameters.put("UPDATED_ON", note.getUpdatedOn() + "");
                if (note.getReaction() != null) {
                    parameters.put("REACTION", note.getReaction().name());
                }
                digestSectionItem.setParameters(parameters);
                digestSectionItem.setSequence(sequence++);
                items.add(digestSectionItem);
            }
            digestSection.setItems(items);
        } else {
            LOGGER.info("Tour Feedback NO Notes Found");
        }
        sections.add(digestSection);
        digestData.setSections(sections);
        digestEmailNotification.setDigestData(digestData);
        digestEmailNotification.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        Email email = new Email();
        email.setFromDisplayName(tourFeedbackJmxConfig.getSendEmailFromDisplayName());
        email.setFromEmail(fromEmail);
        Map<String, String> parameterMap = getCommonParameterMap(tourFeedback, agentTask);
        email.setParameterMap(parameterMap);
        EmailRecipients recipients = getRecipients(to, fromEmail);
        email.setRecipients(recipients);
        String location = parameterMap.get("LOCATION");
        if (parameterMap.get(HIDE_ADDRESS) != null && parameterMap.get(HIDE_ADDRESS).equalsIgnoreCase("true")) {
            location = "(Address not public)";
        }
        String subject = getEmailSubject(location);
        email.setSubject(subject);
        parameterMap.put("EMAIL_SUBJECT", subject);
        digestEmailNotification.setEmail(email);
        digestEmailNotification.setMessageTypeName(TOUR_FEEDBACK_DIGEST_NOTIFICATION);
        digestEmailNotification.setTriggerOn(System.currentTimeMillis() + 10000);
        return digestEmailNotification;
    }

    private EmailRecipients getRecipients(final String to, final String cc) {
        EmailRecipients recipients = new EmailRecipients();
        List<String> toList = new ArrayList<String>();
        toList.add(to);
        List<String> ccList = new ArrayList<String>();
        ccList.add(cc);
        recipients.setToList(toList);
        recipients.setCcList(ccList);
        if (StringUtils.isNotBlank(tourFeedbackJmxConfig.getBccEmail())) {
            List<String> bccList = new ArrayList<String>();
            bccList.add(tourFeedbackJmxConfig.getBccEmail());
            recipients.setBccList(bccList);
        }
        return recipients;
    }

    private Map<String, String> getCommonParameterMap(TourFeedback tourFeedback, Task agentTask ) {
        String location = "";
        Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("PROPERTY_ID", tourFeedback.getPropertyId());
        parameterMap.put("BUYER_ID", tourFeedback.getBuyerId());
        parameterMap.put("TASK_ID", tourFeedback.getTaskId());
        if (agentTask != null && agentTask.getDueDtm() != null) {
            String timeZone = agentDetailsService.getAgentsTimeZoneByAgentId(tourFeedback.getAgentId());
            parameterMap.put("TOUR_TIME", getDateTimeString(agentTask.getDueDtm(), timeZone));
        }
        if (tourFeedback.getOverallRating() != null) {
            parameterMap.put("OVERALL_RATING", tourFeedback.getOverallRating().toString());
        }
        parameterMap.put("OVERALL_COMMENT", tourFeedback.getOverallComment());
        if (tourFeedback.getPropertyId() != null) {
            PropertyDetailsResponse propertyDetailsResponse = propertyService
                    .getPropertyDetails(tourFeedback.getPropertyId());
            if (propertyDetailsResponse != null && propertyDetailsResponse.getData() != null) {
                if (!CollectionUtils.isEmpty(propertyDetailsResponse.getData().getImages())) {
                    parameterMap.put("PROPERTY_IMG",
                            propertyDetailsResponse.getData().getImages().get(0).getThumbnailImageURL());
                }
                parameterMap.put("LIST_PRICE", propertyDetailsResponse.getData().getPrice());
                parameterMap.put("BED", propertyDetailsResponse.getData().getBedRooms());
                if (propertyDetailsResponse.getData().getBathRooms() != null) {
                    parameterMap.put("BATH", propertyDetailsResponse.getData().getBathRooms().toPlainString());
                }
                parameterMap.put("SQ_FT", propertyDetailsResponse.getData().getSize());
                parameterMap.put("MLS_ID", propertyDetailsResponse.getData().getMlsID());
                parameterMap.put("MLS_LOGO", propertyDetailsResponse.getData().getMlsBoardImageURL());
                parameterMap.put( HIDE_ADDRESS, propertyDetailsResponse.getData().isHideAddress() + "");
                parameterMap.put( USER_ID, tourFeedback.getUserId());
                parameterMap.put( TASK_ID, tourFeedback.getTaskId());
                parameterMap.put( TOUR_FEEDBACK_ID, tourFeedback.getId());
                if (propertyDetailsResponse.getData().getPropertyAddress() != null) {
                    parameterMap.put("STREET_NUM",
                            propertyDetailsResponse.getData().getPropertyAddress().getAddressLine1());
                    parameterMap.put("STREET_NAME",
                            propertyDetailsResponse.getData().getPropertyAddress().getAddressLine2());
                    parameterMap.put("CITY", propertyDetailsResponse.getData().getPropertyAddress().getCity());
                    parameterMap.put("STATE_CODE", propertyDetailsResponse.getData().getPropertyAddress().getState());
                    parameterMap.put("ZIP", propertyDetailsResponse.getData().getPropertyAddress().getZip());
                    if (StringUtils
                            .isNotBlank(propertyDetailsResponse.getData().getPropertyAddress().getAddressLine1())) {
                        location = location + propertyDetailsResponse.getData().getPropertyAddress().getAddressLine1()
                                + ", ";
                    }
                    if (StringUtils
                            .isNotBlank(propertyDetailsResponse.getData().getPropertyAddress().getAddressLine2())) {
                        location = location + propertyDetailsResponse.getData().getPropertyAddress().getAddressLine2()
                                + ", ";
                    }
                    location = location + propertyDetailsResponse.getData().getPropertyAddress().getCity() + ", "
                            + propertyDetailsResponse.getData().getPropertyAddress().getState() + ", "
                            + propertyDetailsResponse.getData().getPropertyAddress().getZip();
                    parameterMap.put("LIST_URL",
                            propertyService.getPdpUrl(propertyDetailsResponse, tourFeedback.getPropertyId()));
                } else {
                    LOGGER.error("INFO Tour Feedback Property Address NOT found tourFeedback : {}, ",
                            JsonUtil.toJson(tourFeedback));
                }
            } else {
                LOGGER.error("INFO Tour Feedback Tour Feedback Property Details NOT found tourFeedback : {}",
                        JsonUtil.toJson(tourFeedback));
            }
        }
        if (StringUtils.isBlank(location) && agentTask != null && StringUtils.isNotBlank(agentTask.getLocation())) {
            location = agentTask.getLocation();
        }
        parameterMap.put("LOCATION", location);
        LOGGER.error("INFO Tour Feedback tourFeedback : {}, parameterMap : {}", JsonUtil.toJson(tourFeedback),
                parameterMap.toString());
        return parameterMap;
    }

    @Override
    public void processFeedbackEmails(String feedbackId) {
        try {
            long t1 = System.currentTimeMillis();
            String lockKey = TOUR_FEEDBACK_EMAIL_PROCESS_LOCK + feedbackId;
            LOGGER.info("Tour Feedback Acquire lock to send email for feedbackId: {}, lockKey : {}", feedbackId,
                    lockKey);
            if (tourFeedbackJmxConfig.isSendEmailFlag()) {
                String lockKeyTrimmed = lockKey;
                // this is required since the lockKey string can go beyond 50
                // chars and dsync have len limit of 50
                if (lockKey.length() > 50) {
                    lockKeyTrimmed = lockKey.substring(0, 50);
                }
                if (databaseDistributedSynchronizationService.acquireLock(lockKeyTrimmed)) {
                    try {
                        TourFeedback tourFeedback = tourFeedbackService.getTourFeedback(feedbackId);
                        if (tourFeedback != null && !tourFeedback.getIsEmailSent()) {
                            LOGGER.info("Tour Feedback Sending email for feedbackId: {}", feedbackId);
                            this.sendActualEmail(feedbackId, tourFeedback);
                        } else {
                            LOGGER.error("Tour Feedback NOT found feedbackId: {}", feedbackId);
                        }
                    } catch (Exception e) {
                        LOGGER.error("Tour Feedback Error in processing tour feedback email, feedbackId : {}",
                                feedbackId, e);
                    } finally {
                        databaseDistributedSynchronizationService.releaseLock(lockKeyTrimmed);
                    }
                } else {
                    LOGGER.info("Tour Feedback Operation already in progress for feedbackId : {}, lockKeyTrimmed : {}",
                            feedbackId, lockKeyTrimmed);
                }
                LOGGER.error(
                        "INFO Tour Feedback processFeedbackEmails Completed - feedbackId : {}, Time Consumed : {} ",
                        feedbackId, (System.currentTimeMillis() - t1));
            }
        } catch (Exception e) {
            LOGGER.error("Tour Feedback processFeedbackEmails Failed - feedbackId : {} ", feedbackId);
        }
    }

    private String getDateTimeString(long timestamp, String timezone) {
        String formattedDateTime = "";
        try {
            LOGGER.info("formatting time for agent timestamp : {}, timezone : {}", timestamp, timezone);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TOUR_TIME_FORMAT_STRING);
            if (timezone != null) {
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
            }
            formattedDateTime = simpleDateFormat.format(new Date(timestamp));
            LOGGER.info("formatted time for agent timezone : {}", formattedDateTime);
        } catch (Exception e) {
            LOGGER.error("Error while getting tour time : {}, timezone : {}", timestamp, timezone, e);
        }
        return formattedDateTime;
    }
}
