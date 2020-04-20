package com.owners.gravitas.domain;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Agent.
 *
 * @author amits
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class Agent extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4555437915930490788L;

    /** The info. */
    private AgentInfo info;

    /** The contacts. */
    private Map< String, Contact > contacts = new HashMap<>();

    /** The opportunities. */
    private Map< String, Opportunity > opportunities = new HashMap<>();

    /** The requests. */
    private Map< String, Request > requests = new HashMap<>();

    /** The agent notes. */
    private Map< String, Note > agentNotes = new HashMap<>();

    /** The tasks. */
    private Map< String, Task > tasks = new HashMap<>();

    /**
     * Gets the info.
     *
     * @return the info
     */
    public AgentInfo getInfo() {
        return info;
    }

    /**
     * Sets the info.
     *
     * @param info
     *            the new info
     */
    public void setInfo( AgentInfo info ) {
        this.info = info;
    }

    /**
     * Gets the contacts.
     *
     * @return the contacts
     */
    public Map< String, Contact > getContacts() {
        return contacts;
    }

    /**
     * Sets the contacts.
     *
     * @param contacts
     *            the contacts
     */
    public void setContacts( Map< String, Contact > contacts ) {
        this.contacts = contacts;
    }

    /**
     * Gets the opportunities.
     *
     * @return the opportunities
     */
    public Map< String, Opportunity > getOpportunities() {
        return opportunities;
    }

    /**
     * Sets the opportunities.
     *
     * @param opportunities
     *            the opportunities
     */
    public void setOpportunities( Map< String, Opportunity > opportunities ) {
        this.opportunities = opportunities;
    }

    /**
     * Gets the requests.
     *
     * @return the requests
     */
    public Map< String, Request > getRequests() {
        return requests;
    }

    /**
     * Sets the requests.
     *
     * @param requests
     *            the requests
     */
    public void setRequests( Map< String, Request > requests ) {
        this.requests = requests;
    }

    /**
     * Adds the contact.
     *
     * @param contactId
     *            the contact id
     * @param contact
     *            the contact
     */
    public void addContact( String contactId, Contact contact ) {
        contacts.put( contactId, contact );
    }

    /**
     * Adds the opportunity.
     *
     * @param opportunityId
     *            the opportunity id
     * @param opportunity
     *            the opportunity
     */
    public void addOpportunity( String opportunityId, Opportunity opportunity ) {
        opportunities.put( opportunityId, opportunity );
    }

    /**
     * Adds the request.
     *
     * @param requestId
     *            the request id
     * @param request
     *            the request
     */
    public void addRequest( String requestId, Request request ) {
        requests.put( requestId, request );
    }

    /**
     * Adds the task.
     *
     * @param taskId
     *            the task id
     * @param task
     *            the task
     */
    public void addTask( String taskId, Task task ) {
        tasks.put( taskId, task );
    }

    /**
     * Gets the agent notes.
     *
     * @return the agent notes
     */
    public Map< String, Note > getAgentNotes() {
        return agentNotes;
    }

    /**
     * Sets the agent notes.
     *
     * @param agentNotes
     *            the agent notes
     */
    public void setAgentNotes( Map< String, Note > agentNotes ) {
        this.agentNotes = agentNotes;
    }

    /**
     * Adds the agent note.
     *
     * @param AgentNoteId
     *            the agent note id
     * @param note
     *            the note
     */
    public void addAgentNote( String AgentNoteId, Note note ) {
        agentNotes.put( AgentNoteId, note );
    }

    /**
     * Gets the tasks.
     *
     * @return the tasks
     */
    public Map< String, Task > getTasks() {
        return tasks;
    }

    /**
     * Sets the tasks.
     *
     * @param tasks
     *            the tasks to set
     */
    public void setTasks( Map< String, Task > tasks ) {
        this.tasks = tasks;
    }
}
