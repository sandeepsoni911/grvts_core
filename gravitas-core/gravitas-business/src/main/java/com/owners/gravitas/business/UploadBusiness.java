package com.owners.gravitas.business;

import org.springframework.web.multipart.MultipartFile;

import com.hubzu.notification.dto.ResponseData;
import com.hubzu.notification.dto.client.email.AttachmentUploadRequest;
import com.hubzu.notification.dto.client.email.AttachmentUploadResponse;

public interface UploadBusiness {

    public ResponseData< AttachmentUploadResponse > uploadFiles( final String agentId, final String opportunityId,
            final String taskId, AttachmentUploadRequest attachmentUploadRequest );

    public ResponseData<String> uploadFiles(MultipartFile file, String url);

}
