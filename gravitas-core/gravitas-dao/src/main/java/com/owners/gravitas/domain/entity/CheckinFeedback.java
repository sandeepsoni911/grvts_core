package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The Class CheckinFeedback.
 *
 * @author amits
 */
@Entity( name = "GR_CHECKIN_FEEDBACK" )
public class CheckinFeedback extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 27690713765985556L;

    /** The ref type. */
    @ManyToOne
    @JoinColumn( name = "TASK_ID", insertable = true, updatable = true )
    private AgentTask task;

    /** The question Code. */
    @ManyToOne
    @JoinColumn( name = "QUESTION_ID", insertable = true, updatable = true )
    private RefCode questionCode;

    /** The answer. */
    @Column( name = "ANSWER", updatable = true )
    private String answer;

    /**
     * Gets the task.
     *
     * @return the task
     */
    public AgentTask getTask() {
        return task;
    }

    /**
     * Sets the task.
     *
     * @param task
     *            the new task
     */
    public void setTask( AgentTask task ) {
        this.task = task;
    }

    /**
     * Gets the question Code.
     *
     * @return the question Code
     */
    public RefCode getQuestionCode() {
        return questionCode;
    }

    /**
     * Sets the question Code.
     *
     * @param questionCode
     *            the new question Code
     */
    public void setQuestionRef( RefCode questionCode ) {
        this.questionCode = questionCode;
    }

    /**
     * Gets the answer.
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the answer.
     *
     * @param answer
     *            the new answer
     */
    public void setAnswer( String answer ) {
        this.answer = answer;
    }

    /**
     * Sets the question code.
     *
     * @param questionCode
     *            the new question code
     */
    public void setQuestionCode( RefCode questionCode ) {
        this.questionCode = questionCode;
    }
}
