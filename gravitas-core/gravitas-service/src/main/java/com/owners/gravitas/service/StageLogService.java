package com.owners.gravitas.service;

/**
 * The Interface StageLogService.
 */
public interface StageLogService {

    /**
     * Save opportunity stagelog.
     *
     * @param opportunityId the opportunity id
     * @param stageStr the stage str
     */
    void saveOpportunityStagelog( String opportunityId, String stageStr );

}
