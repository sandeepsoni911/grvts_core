package com.owners.gravitas.enums;

/**
 * The Enum TopicName that keeps the readable names of all the topics.
 *
 * @author ankusht
 */
public enum TopicName {

    /** The lead. */
    LEAD( "Leads Topic", "salesforce.lead.topic" ),

    /** The lead create. */
    LEAD_CREATE( "Leads Create Topic", "salesforce.lead.create.topic" ),

    /** The opportunity create. */
    OPPORTUNITY_CREATE( "Opportunity Create Topic", "salesforce.opportunity.create.topic" ),

    /** The opportunity change. */
    OPPORTUNITY_CHANGE( "Opportunity Change Topic", "salesforce.opportunity.change.topic" ),

    /** The contact. */
    CONTACT( "Contact Topic", "salesforce.contact.change.topic" ),

    /** The agent. */
    AGENT( "Agent Topic", "salesforce.agent.create.topic" );

    /** The name. */
    private final String name;

    /** The property name. */
    private final String propertyName;

    /**
     * Instantiates a new topic name.
     *
     * @param name
     *            the name
     * @param propertyName
     *            the property name
     */
    private TopicName( final String name, final String propertyName ) {
        this.name = name;
        this.propertyName = propertyName;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the property name.
     *
     * @return the property name
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Gets the topic name.
     *
     * @param name
     *            the name
     * @return the topic name
     */
    public static TopicName getTopicName( final String name ) {
        for ( final TopicName topicName : TopicName.values() ) {
            if (topicName.getName().equals( name ))
                return topicName;
        }
        return null;
    }
}
