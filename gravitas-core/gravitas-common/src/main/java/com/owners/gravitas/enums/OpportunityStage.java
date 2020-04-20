package com.owners.gravitas.enums;

/**
 * The Enum OpportunityStage.
 */
public enum OpportunityStage {
    /** prospecting state. */
    PROSPECTING( "Prospecting" );

    /** The stage. */
    private String stage;

    /**
     * Instantiates a new opportunity stage.
     *
     * @param crmStages
     *            the crm stages
     */
    private OpportunityStage( final String crmStages ) {
        this.stage = crmStages;
    }

    /**
     * Gets the stages.
     *
     * @return the stages
     */
    public String getStage() {
        return stage;
    }
}
