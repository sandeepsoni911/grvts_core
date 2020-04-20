package com.owners.gravitas.service;

import org.springframework.web.multipart.MultipartFile;

import com.hubzu.notification.dto.client.email.AttachmentUploadRequest;
import com.hubzu.notification.dto.client.email.AttachmentUploadResponse;

public interface S3UploadService {

    public AttachmentUploadResponse uploadFiles( final String agentId, final String opportunityId, final String taskId,
            AttachmentUploadRequest attachmentUploadRequest );

    public void uploadFiles(MultipartFile file, String signedUrl);

}
