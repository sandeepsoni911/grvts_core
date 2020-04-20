package com.owners.gravitas.util;

public class GravitasUtil {

    public static final String getFilePath( final String clientId, final String prefix, final String agentId,
            final String opportunityId, final String taskId, final String fileName ) {
        return clientId + "/" + prefix + "/" + agentId + "/" + opportunityId + "/" + taskId + "/" + fileName;
    }
}
