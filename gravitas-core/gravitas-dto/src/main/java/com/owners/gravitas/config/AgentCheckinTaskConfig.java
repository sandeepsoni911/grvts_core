package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

@ManagedResource( objectName = "com.owners.gravitas:name=AgentCheckinTaskConfig" )
@Component
public class AgentCheckinTaskConfig {

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The from date. */
    @Value( "${agent.checkin.task.from.date: 2016-01-01}" )
    private String fromDate;

    /** The to date. */
    @Value( "${agent.checkin.task.to.date: 2020-01-01}" )
    private String toDate;

    /** The disallow 1099 agents to delete tasks. */
    @Value( "${agent.checkin.task.disallow.1099agents.to.delete.tasks:false}" )
    private boolean disallow1099AgentsToDeleteTasks;

    /**
     * Gets the from date.
     *
     * @return the from date
     */
    @ManagedAttribute
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Sets the from date.
     *
     * @param fromDate
     *            the new from date
     */
    @ManagedAttribute
    public void setFromDate( final String fromDate ) {
        this.fromDate = fromDate;
        propertyWriter.saveJmxProperty( "agent.checkin.task.from.date", fromDate );
    }

    /**
     * Gets the to date.
     *
     * @return the to date
     */
    @ManagedAttribute
    public String getToDate() {
        return toDate;
    }

    /**
     * Sets the to date.
     *
     * @param toDate
     *            the new to date
     */
    @ManagedAttribute
    public void setToDate( final String toDate ) {
        this.toDate = toDate;
        propertyWriter.saveJmxProperty( "agent.checkin.task.to.date", toDate );
    }

    /**
     * Checks if is disallow 1099 agents to delete tasks.
     *
     * @return true, if is disallow 1099 agents to delete tasks
     */
    @ManagedAttribute
    public boolean isDisallow1099AgentsToDeleteTasks() {
        return disallow1099AgentsToDeleteTasks;
    }

    /**
     * Sets the disallow 1099 agents to delete tasks.
     *
     * @param disallow1099AgentsToDeleteTasks
     *            the new disallow 1099 agents to delete tasks
     */
    @ManagedAttribute
    public void setDisallow1099AgentsToDeleteTasks( final boolean disallow1099AgentsToDeleteTasks ) {
        this.disallow1099AgentsToDeleteTasks = disallow1099AgentsToDeleteTasks;
        propertyWriter.saveJmxProperty( "agent.checkin.task.disallow.1099agents.to.delete.tasks",
                disallow1099AgentsToDeleteTasks );
    }
}
