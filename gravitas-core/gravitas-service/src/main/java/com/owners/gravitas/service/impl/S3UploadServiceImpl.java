package com.owners.gravitas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hubzu.common.logger.HubzuLog;
import com.hubzu.notification.dto.client.email.Attachment;
import com.hubzu.notification.dto.client.email.AttachmentSource;
import com.hubzu.notification.dto.client.email.AttachmentUploadRequest;
import com.hubzu.notification.dto.client.email.AttachmentUploadResponse;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.enums.GravitasStoragePrefixType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.AmazonS3Service;
import com.owners.gravitas.service.S3UploadService;
import com.owners.gravitas.util.GravitasUtil;

@Service
public class S3UploadServiceImpl implements S3UploadService {

    private static final HubzuLog LOGGER = HubzuLog.getLogger(S3UploadServiceImpl.class);

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Value("${gravitas.amazon.s3.tour_feedback.image_bucket_name:gravitas-feeback}")
    private String tourFeedbackEmailImageS3BucketName;

    @Override
    public AttachmentUploadResponse uploadFiles(final String agentId, final String opportunityId, final String taskId,
            AttachmentUploadRequest attachmentUploadRequest) {
        LOGGER.info("Uploading files for agentId : {}, opportunityId : {}, taskId : {}, uploadRequest : {}", agentId,
                opportunityId, taskId, attachmentUploadRequest);
        List<MultipartFile> files = attachmentUploadRequest.getFiles();
        String requestId = attachmentUploadRequest.getRequestId();
        AttachmentUploadResponse attachmentUploadResponse = new AttachmentUploadResponse();
        attachmentUploadResponse.setRequestId(requestId);
        List<Attachment> attachmentResponseList = new ArrayList<>();
        attachmentUploadResponse.setFileDetails(attachmentResponseList);
        for (MultipartFile multipartFile : files) {
            LOGGER.debug("Uploading file: {}", multipartFile.getOriginalFilename());
            String fileName = attachmentUploadRequest.getCreatedOn().getTime() + "-"
                    + multipartFile.getOriginalFilename();
            String filePath = GravitasUtil.getFilePath(attachmentUploadRequest.getClientId(),
                    GravitasStoragePrefixType.IMAGES.toString(), agentId, opportunityId, taskId, fileName);
            try {
                amazonS3Service.putFile(filePath, tourFeedbackEmailImageS3BucketName, multipartFile.getInputStream(),
                        multipartFile.getContentType(), multipartFile.getSize());
                Attachment currentAttachment = new Attachment();
                currentAttachment.setFileName(fileName);
                currentAttachment.setFilePath(filePath);
                currentAttachment.setSource(AttachmentSource.S3);
                attachmentResponseList.add(currentAttachment);
            } catch (Exception e) {
                LOGGER.error("Failed file : " + multipartFile.getOriginalFilename() + " requestId : " + requestId, e);
                throw new ApplicationException(
                        "Failed file : " + multipartFile.getOriginalFilename() + " requestId : " + requestId,
                        ErrorCode.ATTACHMENT_UPLOAD_FAILURE);
            }
        }
        return attachmentUploadResponse;
    }

    @Override
    public void uploadFiles(MultipartFile file, String signedUrl) {
        try {
            amazonS3Service.uploadUsingSignedUrl(signedUrl, file.getInputStream());
        } catch (Exception e) {
            LOGGER.error("Upload Failed signedUrl : {}", signedUrl, e);
            throw new ApplicationException("Upload Failed signedUrl : " + signedUrl + ", message : " + e.getMessage(),
                    ErrorCode.ATTACHMENT_UPLOAD_FAILURE);
        }
    }
}
