package com.owners.gravitas.constants;

import static com.owners.gravitas.constants.Constants.AGENT_EMAIL;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.CONTACT_EMAIL;
import static com.owners.gravitas.constants.Constants.CONTACT_ID;
import static com.owners.gravitas.constants.Constants.CRM_OPPORTUNITY_ID;
import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.constants.Constants.FROM_DTM;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY_ID;

/**
 * The Interface FirebaseQuery.
 */
public final class FirebaseQuery {

    /** The Constant GET_AGENT_BY_EMAIL. */
    public static final String GET_AGENT_BY_EMAIL = "agents.json?orderBy=\"info/email\"&equalTo=\"{" + EMAIL + "}\"";

    /** The Constant GET_SEARCH_BY_CONTACT_EMAIL. */
    public static final String GET_SEARCH_BY_CONTACT_EMAIL = "search.json?orderBy=\"contactEmail\"&equalTo=\"{"
            + CONTACT_EMAIL + "}\"";

    /** The Constant GET_SEARCH_BY_AGENT_ID. */
    public static final String GET_SEARCH_BY_AGENT_ID = "search.json?orderBy=\"agentId\"&equalTo=\"{" + AGENT_ID
            + "}\"&limitToFirst=1";

    /** The Constant GET_SEARCH_BY_OPPORTUNITY_ID. */
    public static final String GET_SEARCH_BY_CRM_OPPORTUNITY_ID = "search.json?orderBy=\"crmOpportunityId\"&equalTo=\"{"
            + CRM_OPPORTUNITY_ID + "}\"";

    /** The Constant GET_SEARCH_BY_OPPORTUNITY_ID. */
    public static final String GET_SEARCH_BY_OPPORTUNITY_ID = "search.json?orderBy=\"opportunityId\"&equalTo=\"{"
            + OPPORTUNITY_ID + "}\"";

    /** The Constant GET_SEARCH_BY_AGENT_EMAIL. */
    public static final String GET_SEARCH_BY_AGENT_EMAIL = "search.json?orderBy=\"agentEmail\"&equalTo=\"{"
            + AGENT_EMAIL + "}\"&limitToFirst=1";

    /** The Constant GET_SEARCH_BY_CONTACT_ID. */
    public static final String GET_SEARCH_BY_CONTACT_ID = "search.json?orderBy=\"contactId\"&equalTo=\"{" + CONTACT_ID
            + "}\"";

    /** The Constant GET_AGENTS_NEW_OPPORTUNITY. */
    public static final String GET_AGENTS_NEW_OPPORTUNITY = "/agents/%s/opportunities.json?orderBy=\"stage\"&equalTo=\"New\"";

    /** The Constant GET_AGENTS_NEW_OPPORTUNITY. */
    public static final String GET_AGENTS_CLAIMED_OPPORTUNITY = "/agents/%s/opportunities.json?orderBy=\"stage\"&equalTo=\"Claimed\"";

    /** The Constant GET_REQUESTS_BY_OPPORTUNITY_ID. */
    public static final String GET_REQUESTS_BY_OPPORTUNITY_ID = "agents/{" + AGENT_ID
            + "}/requests.json?orderBy=\"opportunityId\"&equalTo=\"{" + OPPORTUNITY_ID + "}\"";

    /** The Constant GET_TASKS_BY_OPPORTUNITY_ID. */
    public static final String GET_TASKS_BY_OPPORTUNITY_ID = "agents/{" + AGENT_ID
            + "}/tasks.json?orderBy=\"opportunityId\"&equalTo=\"{" + OPPORTUNITY_ID + "}\"";

    /** The Constant GET_ALL_AGENT_ID. */
    public static final String GET_ALL_AGENT_ID = "agents.json?shallow=true";

    /** The Constant AGENT_ID_EXIST. */
    public static final String AGENT_ID_EXIST = "agents/{" + AGENT_ID + "}.json?shallow=true";

    /** The Constant GET_AGENT_BY_ID. */
    public static final String GET_AGENT_BY_ID = "agents/{" + AGENT_ID + "}.json";

    /** The Constant GET_REF_DATA. */
    public static final String GET_REF_DATA = "refdata.json?shallow=true";

    /** The Constant GET_AGENT_EMAIL. */
    public static final String GET_AGENT_EMAIL = "agents/{" + AGENT_ID + "}/info/email.json?shallow=true";

    /** The Constant GET_OPPORTUNITIES_BY_AGENT_ID. */
    public static final String GET_OPPORTUNITIES_BY_AGENT_ID = "agents/{" + AGENT_ID
            + "}/opportunities.json?orderBy=\"assignedDtm\"&startAt={" + FROM_DTM + "}";

    /** The Constant GET_CONTACT_BY_ID. */
    public static final String GET_CONTACT_BY_ID = "agents/{" + AGENT_ID + "}/contacts/{" + CONTACT_ID + "}.json";


    /**
     * Instantiates a new Firebase query.
     */
    private FirebaseQuery() {
        // does noting
    }
}
