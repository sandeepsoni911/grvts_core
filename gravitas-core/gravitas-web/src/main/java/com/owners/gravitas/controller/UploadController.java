package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.UserPermission.UPLOAD_TOUR_IMAGES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hubzu.common.logger.HubzuLog;
import com.hubzu.notification.common.util.JsonUtil;
import com.hubzu.notification.dto.ResponseData;
import com.hubzu.notification.dto.client.email.AttachmentSource;
import com.hubzu.notification.dto.client.email.AttachmentUploadRequest;
import com.hubzu.notification.dto.client.email.AttachmentUploadResponse;
import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.annotation.ReadArgs;
import com.owners.gravitas.business.UploadBusiness;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.validator.GenericValidator;
import com.owners.gravitas.validator.UserRoleValidator;

@RestController
public class UploadController extends BaseController {

    private static final HubzuLog LOGGER = HubzuLog.getLogger(UploadController.class);

    @Autowired
    GenericValidator<AttachmentUploadRequest> uploadRequestValidator;

    @Autowired
    UploadBusiness uploadBusiness;

    /** The role validator. */
    @Autowired
    private UserRoleValidator roleValidator;

    @CrossOrigin
    @RequestMapping(value = "/agents/{agentId}/opportunities/{opportunityId}/tasks/{taskId}/upload", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    @ReadArgs
    @PerformanceLog
    @Secured({ UPLOAD_TOUR_IMAGES })
    public ResponseData<AttachmentUploadResponse> uploadFiles(@PathVariable final String agentId,
            @PathVariable final String opportunityId, @PathVariable final String taskId,
            @RequestParam List<MultipartFile> files, @RequestParam String clientId) throws ApplicationException {
        roleValidator.validateByAgentId(agentId);
        AttachmentUploadRequest uploadRequest = new AttachmentUploadRequest();
        uploadRequest.setFiles(files);
        uploadRequest.setClientId(clientId);
        uploadRequest.setAttachmentDestination(AttachmentSource.S3);
        LOGGER.info("Request UploadRequest : {} " + JsonUtil.toJsonString(uploadRequest));
        ResponseData<AttachmentUploadResponse> dataResponse = new ResponseData<AttachmentUploadResponse>();
        if (uploadRequestValidator.isValid(uploadRequest)) {
            dataResponse = uploadBusiness.uploadFiles(agentId, opportunityId, taskId, uploadRequest);
        }
        LOGGER.info("Response UploadRequest : {} " + JsonUtil.toJsonString(dataResponse));
        return dataResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/upload_signed_image", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    @ReadArgs
    @PerformanceLog
    @Secured({ UPLOAD_TOUR_IMAGES })
    public ResponseData<String> uploadSignedFiles(@RequestParam MultipartFile file, @RequestParam String signedUrl)
            throws ApplicationException {
        LOGGER.info("Signed Upload Request - signedUrl : {} ", signedUrl);
        ResponseData<String> status = uploadBusiness.uploadFiles(file, signedUrl);
        LOGGER.info("Signed Upload Request - signedUrl : {}, status : {}", signedUrl, status);
        return status;
    }

}
