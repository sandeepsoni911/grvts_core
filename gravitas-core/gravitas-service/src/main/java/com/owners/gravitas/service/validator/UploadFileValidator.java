package com.owners.gravitas.service.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hubzu.notification.common.constant.ErrorMessageConstant;
import com.hubzu.notification.dto.client.email.AttachmentUploadRequest;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The class UploadFileValidator.
 *
 * @author bhardrah
 *
 * @version 0.1
 */
@Service( "uploadFileValidator" )
public class UploadFileValidator {
    /** The file size limit. */
    @Value( "${amazon.filesizelimit}" )
    private long fileSizeLimit;

    /** The maximum number of attachments. */
    @Value( "${amazon.maximumFiles}" )
    private int maximumNumberofFiles;

    /** The cumulative size of attachments. */
    @Value( "${amazon.cumulativesize}" )
    private long cumulativeSizeofFiles;

    public void validateUploadRequest( final AttachmentUploadRequest uploadRequest ) {
        List< MultipartFile > files = uploadRequest.getFiles();
        String requestId = uploadRequest.getRequestId();
        checkforCumulativeSize( files, requestId );
        checkforFileSizeLimit( files, requestId );
    }

    private void checkforFileSizeLimit( List< MultipartFile > files, final String requestId ) {
        if (!CollectionUtils.isEmpty( files )) {
            long cumulativeSize = 0;
            List< String > errorList = new ArrayList< String >();
            for ( MultipartFile attachment : files ) {
                try {
                    long size = attachment.getSize();
                    if (size > fileSizeLimit) {
                        errorList.add( ErrorMessageConstant.ATTACHMENT_SIZE_NOT_PERMITTED + " : "
                                + attachment.getOriginalFilename() );
                    }
                    cumulativeSize = size + cumulativeSize;
                } catch ( Exception e ) {
                    errorList.add( ErrorMessageConstant.ATTACHMENT_UPLOAD_ERROR );
                    break;
                }
            }
            if (cumulativeSize > cumulativeSizeofFiles) {
                errorList.add( ErrorMessageConstant.ATTACHMENT_SIZE_NOT_PERMITTED + ": " + cumulativeSize );
            }
            if (!errorList.isEmpty()) {
                throw new ApplicationException( errorList.toString() + " for request id : " + requestId,
                        ErrorCode.INVALID_INPUT );
            }
        }
    }

    /**
     * Check for cumulative size.
     *
     * @param attachmentParameterMap
     *            the attachment parameter map
     * @param errorList
     *            the error list
     */
    private void checkforCumulativeSize( final List< MultipartFile > files, final String requestId ) {
        if (!CollectionUtils.isEmpty( files )) {
            if (files.size() > maximumNumberofFiles) {
                throw new ApplicationException(
                        ErrorMessageConstant.ATTACHMENT_LIMIT_NOT_PERMITTED + " for request id : " + requestId,
                        ErrorCode.INVALID_INPUT );
            }
        }
    }

}
