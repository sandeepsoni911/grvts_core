package com.owners.gravitas.service.tour.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.hubzu.common.daemon.service.DaemonScheduler;
import com.hubzu.common.logger.HubzuLog;
import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.hubzu.notification.common.util.JsonUtil;
import com.owners.gravitas.config.tour.TourFeedbackJmxConfig;
import com.owners.gravitas.service.tour.AmazonSqsObjectEventService;
import com.owners.gravitas.service.tour.TourFeedbackService;
import com.zuner.coshopping.model.tour_feedback.TourFeedback;

/**
 * The Class AmazonSqsObjectEventServiceImpl.
 *
 * @author rajputbh
 */
@Service("amazonSqsService")
public class AmazonSqsObjectEventServiceImpl extends DaemonScheduler implements AmazonSqsObjectEventService {
    private static final HubzuLog LOGGER = HubzuLog.getLogger(AmazonSqsObjectEventServiceImpl.class);
    private AmazonSQS sqs = null;
    @Value("${gravitas.amazon.sqs.tour_feedback_s3_events_queue_url}")
    private String s3ObjectEventsQueueUrl;
    @Value("${gravitas.amazon.sqs.access_key}")
    private String accessKey;
    @Value("${gravitas.amazon.sqs.secret_key}")
    private String secretKey;
    @Value("${gravitas.amazon.sqs.region}")
    private String region;
    // below value should not be more than 10
    @Value("${gravitas.amazon.sqs.max_messages_read_count:10}")
    private int maxMsgReadCount;
    @Value("${gravitas.amazon.sqs.visibility_timeout_seconds:300}")
    private int visibilityTimeoutSeconds;
    // below value should not be more than 20
    @Value("${gravitas.amazon.sqs.wait_time_seconds:20}")
    private int waitTimeSeconds;

    @Autowired
    @Qualifier("tourFeedbackS3EventThreadPool")
    private ExecutorService tourFeedbackS3EventThreadPool;
    @Autowired
    private TourFeedbackEmailProcessorImpl tourFeedbackEmailProcessorImpl;
    @Autowired
    private TourFeedbackService tourFeedbackService;
    @Autowired
    private TourFeedbackJmxConfig tourFeedbackJmxConfig;
    @Autowired
    private DistributedSynchronizationService databaseDistributedSynchronizationService;
    private final String TOUR_FEEDBACK_EVENTS_FETCH_LOCK = "TOUR_FEEDBACK_EVENTS_FETCH_LOCK";

    @PostConstruct
    public void initialise() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        sqs = new AmazonSQSClient(credentials);
        Region sqsRegion = Region.getRegion(Regions.fromName(region));
        sqs.setRegion(sqsRegion);
        if (maxMsgReadCount > 10) {
            maxMsgReadCount = 10;
        }
        if (waitTimeSeconds > 20) {
            waitTimeSeconds = 20;
        }
    }

    @Scheduled(initialDelay = 200, fixedDelayString = "${gravitas.amazon.sqs.feedback_scheduler_delay:5000}")
    @Override
    public void processS3ObjectEvent() {
        if (stopScheduler) {
            LOGGER.info(
                    "Tour Feedback  Stoping AmazonSqsObjectEventServiceImpl Job for  processSuccessFeedback API Usage Data to s3ObjectEventsQueueUrl  : {}",
                    new java.sql.Timestamp(System.currentTimeMillis()));
            return;
        }
        try {
            LOGGER.info("Tour Feedback SQS Acquiring Lock : {}", TOUR_FEEDBACK_EVENTS_FETCH_LOCK);
            if (databaseDistributedSynchronizationService.acquireLock(TOUR_FEEDBACK_EVENTS_FETCH_LOCK)) {
                try {
                    LOGGER.info("Tour Feedback SQS Lock Acquired : {}", TOUR_FEEDBACK_EVENTS_FETCH_LOCK);
                    List<Message> messages = fetchMessages(s3ObjectEventsQueueUrl);
                    if (!CollectionUtils.isEmpty(messages)) {
                        LOGGER.info("Tour Feedback SQS s3ObjectEventsQueueUrl : {}", s3ObjectEventsQueueUrl);
                        LOGGER.info("INFO : SQS - Tour Feedback s3ObjectEventsQueueUrl : {}, messageList Count : {}",
                                s3ObjectEventsQueueUrl, messages.size());
                        logFeedback(messages);
                        deleteMessages(s3ObjectEventsQueueUrl, messages);
                    } else {
                        LOGGER.info("Tour Feedback NO events found in SQS s3ObjectEventsQueueUrl : {}",
                                s3ObjectEventsQueueUrl);
                    }
                } catch (Exception e) {
                    LOGGER.error("Tour Feedback SQS s3ObjectEventsQueueUrl : {}", s3ObjectEventsQueueUrl, e);
                } finally {
                    LOGGER.info("Tour Feedback SQS Lock Released : {}", TOUR_FEEDBACK_EVENTS_FETCH_LOCK);
                    databaseDistributedSynchronizationService.releaseLock(TOUR_FEEDBACK_EVENTS_FETCH_LOCK);
                }
            } else {
                LOGGER.info("Tour Feedback SQS Lock Operation already in progress : {}", TOUR_FEEDBACK_EVENTS_FETCH_LOCK);
            }
        } catch (Exception e) {
            LOGGER.error("Tour Feedback SQS s3ObjectEventsQueueUrl : {}", s3ObjectEventsQueueUrl, e);
        }
    }

    private void logFeedback(final List<Message> messageList) {
        try {
            Map<Message, Future<EventProcessingStatus>> results = new HashMap<>();
            for (Message message : messageList) {
                results.put(message, tourFeedbackS3EventThreadPool.submit(new S3EventProcessorTask(message)));
            }
            for (Message message : results.keySet()) {
                EventProcessingStatus eventProcessingStatus = null;
                try {
                    Future<EventProcessingStatus> futureNotificationGatewayResponse = results.get(message);
                    eventProcessingStatus = futureNotificationGatewayResponse.get();
                    LOGGER.info("Tour Feedback message : {}, feedbackLogStatus : {}", JsonUtil.toJsonString(message),
                            eventProcessingStatus);
                } catch (InterruptedException | ExecutionException ex) {
                    LOGGER.error("Tour Feedback message : {}, feedbackLogStatus : {}", JsonUtil.toJsonString(message),
                            eventProcessingStatus, ex);
                }
            }
        } catch (Exception e) {
            LOGGER.error("FAILED to log the s3 object event for messageList : {}", JsonUtil.toJsonString(messageList));
        }
    }

    private void deleteMessages(final String feedbackQueueUrl, final List<Message> messageList) {
        for (Message message : messageList) {
            try {
                String messageReceiptHandle = message.getReceiptHandle();
                LOGGER.info("Tour Feedback Message Deleting From SQS {}", message.getMessageId());
                sqs.deleteMessage(new DeleteMessageRequest(feedbackQueueUrl, messageReceiptHandle));
                LOGGER.info("Tour Feedback Message Deleted From SQS {}", message.getMessageId());
            } catch (Exception e) {
                LOGGER.error("Tour Feedback Error deleting feedback message : {}", message.getMessageId(), e);
            }
        }
    }

    private List<Message> fetchMessages(final String feedbackQueueUrl) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(feedbackQueueUrl);
        receiveMessageRequest.setMaxNumberOfMessages(maxMsgReadCount);
        receiveMessageRequest.setWaitTimeSeconds(waitTimeSeconds);
        receiveMessageRequest.setVisibilityTimeout(visibilityTimeoutSeconds);
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        LOGGER.info("Tour Feedback Fetched Messages : \n{}", JsonUtil.toJsonString(messages));
        return messages;
    }

    private class S3EventProcessorTask implements Callable<EventProcessingStatus> {
        private Message message;

        public S3EventProcessorTask(final Message message) {
            this.message = message;
        }

        @Override
        public EventProcessingStatus call() {
            LOGGER.info("Tour Feedback Gateway Email Feedback Logging Daemon - message : {}",
                    JsonUtil.toJsonString(message));
            try {
                Map<String, String> imageUrlEventMap = getImageUrl(message.getBody());
                // Extract the feedback id from s3 event and call process email
                // only if event for all images is made available
                if (!CollectionUtils.isEmpty(imageUrlEventMap)) {
                    for (String imageUrl : imageUrlEventMap.keySet()) {
                        LOGGER.error("Tour Feedback Recieved for Image imageUrl :{}", imageUrlEventMap.toString());
                        if (imageUrl != null && imageUrl.startsWith("tour_feedbacks")) {
                            String tempString = imageUrl.substring("tour_feedbacks".length() + 1);
                            int index = tempString.indexOf("/");
                            if (index != -1) {
                                /**
                                 * String objectKey = tourFeedback.getId() + "/"
                                 * + note.getId() + "/" + file.getId();
                                 * 
                                 */
                                String feedbackId = tempString.substring(0, index);
                                tempString = tempString.substring(index + 1);
                                index = tempString.indexOf("/");
                                String noteId = tempString.substring(0, index);
                                String fileId = tempString.substring(index + 1);

                                String uploadStatus = "UPLOADED";
                                LOGGER.info("feedbackId :{}, noteId :{}, fileId :{}, uploadStatus :{}", feedbackId,
                                        noteId, fileId, uploadStatus);
                                tourFeedbackService.updateTourFeedbackImageUploadStatus(feedbackId, noteId, fileId,
                                        uploadStatus);
                                TourFeedback tourFeedback = tourFeedbackService.getTourFeedback(feedbackId);
                                if (tourFeedbackJmxConfig.isSendEmailUsingUploadEvents() && tourFeedback != null
                                        && !tourFeedback.getIsEmailSent()
                                        && tourFeedback.getTotalFileCount() == tourFeedback.getUploadedFileCount()) {
                                    tourFeedbackEmailProcessorImpl.processFeedbackEmails(feedbackId);
                                }
                                return EventProcessingStatus.SUCCESS;
                            }
                        }
                    }
                } else {
                    LOGGER.error("ERROR Tour Feedback Gateway Email Feedback Logging Daemon - message : {}",
                            JsonUtil.toJsonString(message));
                }
            } catch (Exception e) {
                LOGGER.error("Tour Feedback Gateway Email Feedback Logging Daemon Image URL is null - message : {}",
                        JsonUtil.toJsonString(message), e);
            }
            return EventProcessingStatus.FAILURE;
        }

        private Map<String, String> getImageUrl(String body) {
            Map<String, String> imageUrlEventMap = new HashMap<String, String>();
            try {
                JsonParser jsonParser = new JsonParser();
                JsonObject resJsonObject = jsonParser.parse(body).getAsJsonObject();
                JsonPrimitive messageJsonPrimitive = resJsonObject.getAsJsonPrimitive("Message");
                JsonObject messageJsonObject = jsonParser.parse(messageJsonPrimitive.getAsString()).getAsJsonObject();
                JsonArray jsonRecordsArray = messageJsonObject.getAsJsonArray("Records");
                if (jsonRecordsArray != null) {
                    for (int i = 0; i < jsonRecordsArray.size(); i++) {
                        JsonElement jsonElement = jsonRecordsArray.get(i);
                        LOGGER.info("ERROR Tour Feedback Gateway Email Feedback Logging Daemon - jsonElement : {}",
                                jsonElement);
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        JsonElement jsonElementS3 = jsonObject.get("s3");
                        JsonElement jsonElementEventName = jsonObject.get("eventName");
                        String eventName = jsonElementEventName.getAsString();
                        JsonObject jsonObjectS3 = jsonElementS3.getAsJsonObject();
                        JsonElement jsonElementObject = jsonObjectS3.get("object");
                        JsonObject jsonObjectObject = jsonElementObject.getAsJsonObject();
                        LOGGER.info("Object Key {}", jsonObjectObject.get("key"));
                        imageUrlEventMap.put(jsonObjectObject.get("key").getAsString(), eventName);
                    }
                } else {
                    LOGGER.error("ERROR Tour Feedback S3 Event read Records FAILED - body : {}",
                            JsonUtil.toJsonString(body));
                }
            } catch (Exception e) {
                LOGGER.error("ERROR Tour Feedback S3 Event read file path FAILED - body : {}",
                        JsonUtil.toJsonString(body), e);
            }
            LOGGER.info("Tour Feedback imageUrlEventMap : {}", imageUrlEventMap);
            return imageUrlEventMap;
        }
    }

    private enum EventProcessingStatus {
        SUCCESS,
        FAILURE;
    }
}
