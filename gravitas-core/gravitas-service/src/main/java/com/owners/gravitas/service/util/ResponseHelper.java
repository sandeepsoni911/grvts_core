package com.owners.gravitas.service.util;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.hubzu.notification.common.enums.ProcessingStatus;
import com.hubzu.notification.dto.ResponseData;
import com.hubzu.notification.dto.Status;

@Service
public class ResponseHelper {

    public <T> ResponseData<T> getResponseData(final T requestId, Status status, ProcessingStatus processingStatus,
            String message, Timestamp timestamp) {
        final ResponseData<T> result = new ResponseData<T>();
        result.setStatus(status);
        result.setStatusCode(processingStatus);
        result.setStatusMessage(message);
        result.setResult(requestId);
        result.setExecutionTime(System.currentTimeMillis() - timestamp.getTime());
        result.setRequestStartTime(timestamp.getTime());
        return result;
    }
    
    public ResponseData getResponseData(final Status status, final ProcessingStatus code, final String message) {
        final ResponseData responseData = new ResponseData();
        responseData.setStatus(status);
        responseData.setStatusCode(code);
        responseData.setStatusMessage(message);
        return responseData;
    }

}
