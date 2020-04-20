package com.owners.gravitas.business.impl;

import java.sql.Timestamp;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hubzu.common.logger.HubzuLog;
import com.hubzu.notification.common.enums.ProcessingStatus;
import com.hubzu.notification.dto.ResponseData;
import com.hubzu.notification.dto.Status;
import com.hubzu.notification.dto.client.email.AttachmentUploadRequest;
import com.hubzu.notification.dto.client.email.AttachmentUploadResponse;
import com.owners.gravitas.business.UploadBusiness;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.S3UploadService;
import com.owners.gravitas.service.util.ResponseHelper;
import com.owners.gravitas.service.validator.UploadFileValidator;

@Service
public class UploadBusinessImpl implements UploadBusiness {

    private static final HubzuLog LOGGER = HubzuLog.getLogger(UploadBusinessImpl.class);

    @Autowired
    S3UploadService s3UploadService;

    @Autowired
    ResponseHelper responseHelper;

    @Autowired
    private UploadFileValidator uploadFileValidator;

    /** The agent task service. */
    @Autowired
    private AgentTaskService agentTaskService;

    @Override
    public ResponseData<AttachmentUploadResponse> uploadFiles(String agentId, String opportunityId, String taskId,
            AttachmentUploadRequest uploadRequest) {
        LOGGER.info("Got upload file request for agentId: {}, opportunityId: {}, taskId : {} and uploadRequest : {}",
                agentId, opportunityId, taskId, uploadRequest);
        agentTaskService.getTaskById(agentId, taskId);
        uploadRequest.setCreatedOn(new Timestamp((new DateTime()).getMillis()));
        uploadRequest.setRequestId(UUID.randomUUID().toString());
        uploadFileValidator.validateUploadRequest(uploadRequest);
        AttachmentUploadResponse uploadAttachments = s3UploadService.uploadFiles(agentId, opportunityId, taskId,
                uploadRequest);
        uploadAttachments.setRequestId(uploadRequest.getRequestId());
        ResponseData<AttachmentUploadResponse> responseData = responseHelper.getResponseData(uploadAttachments,
                Status.SUCCESS, ProcessingStatus.SUCCESS, Status.SUCCESS.getValue(), uploadRequest.getCreatedOn());
        return responseData;
    }

    @Override
    public ResponseData<String> uploadFiles(MultipartFile file, String signedUrl) {
        ResponseData<String> responseData = null;
        try {
            s3UploadService.uploadFiles(file, signedUrl);
            responseData = responseHelper.getResponseData(Status.SUCCESS, ProcessingStatus.SUCCESS,
                    Status.SUCCESS.getValue());
            responseData.setResult("Image Upload for signedUrl : " + signedUrl);
        } catch (Exception e) {
            LOGGER.error("Image Upload Failed  : {}", signedUrl, e);
            responseData = responseHelper.getResponseData(Status.FAILURE, ProcessingStatus.ATTACHMENT_UPLOADING_FAILURE,
                    ProcessingStatus.ATTACHMENT_UPLOADING_FAILURE.getValue());
            responseData.setResult(e.getMessage());
        }
        return responseData;
    }

}
